package com.example.cryptomarket.ui.coins

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.PriceData
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.CoinChartRecyclerItemBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class CoinsListAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val chosenTimeFrame: DateFrame,
    private val resources: Resources,
    private val chartMarker: ChartMarker,
    private val vm: CryptoViewModel
) :
    ListAdapter<Ticker, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder
            .from(viewLifecycleOwner, chosenTimeFrame, resources, chartMarker, vm, parent)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CoinsViewHolder private constructor(
        private val viewLifecycleOwner: LifecycleOwner,
        private val chosenTimeFrame: DateFrame,
        private val resources: Resources,
        private val chartMarker: ChartMarker,
        private val vm: CryptoViewModel,
        private val binding: CoinChartRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ticker: Ticker) {
            populateUI(ticker)
            binding.apply {
                itemContainer.setOnClickListener {
                    vm.setTickerClicked(ticker)
                }
                executePendingBindings()
            }
        }

        private fun populateUI(ticker: Ticker) {
            binding.apply {
                coinSymbolTxt.text = ticker.symbol
                coinNameTxt.text = ticker.name
                rankTxt.text = removeTrailing2Zeros(ticker.rank.toString())
                priceTxt.text = presentPriceFormatUSD("", ticker.quotes.usd.price)
                timeFrame.text = chosenTimeFrame.abbrev
                displayPercentChange(percentChangeTxt, ticker.quotes.usd)
                vm.getHistoricalTickerData(false, ticker.id, chosenTimeFrame)
                    .observe(viewLifecycleOwner) { tickerData ->
                        makeLineChart(itemLineChart, tickerData)
                    }
            }
        }

        // HELPERS //
        private fun displayPercentChange(percentChangeTxt: TextView, priceData: PriceData) {
            val percentChangeText = pickPercentChange(chosenTimeFrame, priceData) ?: ""
            percentChangeTxt.text = percentChangeText
            if (getPercentChangeNum(percentChangeText) > 0.0)
                percentChangeTxt.setTextColor(resources.getColor(R.color.positive_green))
            else if (getPercentChangeNum(percentChangeText) < 0.0)
                percentChangeTxt.setTextColor(resources.getColor(R.color.negative_red))
        }
        // HELPERS //

        // SETUP LINE CHART //
        private fun makeLineChart(chart: LineChart, tickerData: List<HistoricalTicker>) {
            chart.setBackgroundColor(resources.getColor(R.color.color_background))
            chart.setTouchEnabled(false)
            chart.setDrawGridBackground(false)
            // Create marker to display box when values are selected.
            // Set the marker to the chart.
            chartMarker.chartView = chart
            chart.marker = chartMarker
            // Add data to chart
            setLinearData(chart, tickerData)
            // Get the legend (only possible after setting data).
            chart.legend.isEnabled = false
            chart.description.isEnabled = false
        }

        private fun setLinearData(chart: LineChart, tickerData: List<HistoricalTicker>) {
            val set1: LineDataSet?
            val entries = ArrayList<Entry>()
            for (i in tickerData.indices) entries.add(
                Entry(i.toFloat(), tickerData[i].price.toFloat())
            )
            if (chart.data != null && chart.data.dataSetCount > 0) {
                // If data has already been created.
                set1 = chart.data.getDataSetByIndex(0) as LineDataSet
                set1.values = entries
                set1.notifyDataSetChanged()
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
            } else {
                // Create a dataset and give it a type.
                set1 = LineDataSet(entries, "DataSet 1")
                set1.setDrawIcons(false)
                set1.color = resources.getColor(R.color.chart_line_color)
                // Line thickness and point size.
                set1.lineWidth = 3f
                set1.setDrawCircles(false)
                set1.valueTextSize = 9f
                set1.setDrawValues(false)
                val xAxis: XAxis = chart.xAxis
                val yAxis: YAxis = chart.axisLeft
                xAxis.isEnabled = false
                chart.axisRight.isEnabled = false
                chart.axisLeft.isEnabled = false
                xAxis.setDrawGridLines(false)
                yAxis.setDrawGridLines(false)
                val dataSets = ArrayList<ILineDataSet>()
                dataSets.add(set1) // add the data sets
                // Create a data object with the data sets.
                val data = LineData(dataSets)
                // Set data
                chart.data = data
            }
        }
        // SETUP LINE CHART //

        companion object {
            fun from(
                viewLifecycleOwner: LifecycleOwner,
                chosenTimeFrame: DateFrame,
                resources: Resources,
                chartMarker: ChartMarker,
                vm: CryptoViewModel,
                parent: ViewGroup
            ): CoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinChartRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return CoinsViewHolder(
                    viewLifecycleOwner, chosenTimeFrame, resources,
                    chartMarker, vm, binding
                )
            }
        }
    }

    class CoinsDiffCallback : DiffUtil.ItemCallback<Ticker>() {
        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem == newItem
        }
    }
}