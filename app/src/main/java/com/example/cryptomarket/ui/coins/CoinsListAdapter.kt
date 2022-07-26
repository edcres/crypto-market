package com.example.cryptomarket.ui.coins

import android.content.res.Resources
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.CoinChartRecyclerItemBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.pickPercentChange
import com.example.cryptomarket.utils.presentPriceFormatUSD
import com.example.cryptomarket.utils.removeTrailing2Zeros
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class CoinsListAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val chosenTimeFrame: DateFrame,
    private val resources: Resources,
    private val linearMarker: LinearMarker,
    private val vm: CryptoViewModel
) :
    ListAdapter<Ticker, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder
            .from(viewLifecycleOwner, chosenTimeFrame, resources, linearMarker, vm, parent)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CoinsViewHolder private constructor(
        private val viewLifecycleOwner: LifecycleOwner,
        private val chosenTimeFrame: DateFrame,
        private val resources: Resources,
        private val linearMarker: LinearMarker,
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
                // todo: Test if the time frame changes in the list when I click the time frame btns
                timeFrame.text = chosenTimeFrame.abbrev
                percentChangeTxt.text =
                    pickPercentChange(chosenTimeFrame, ticker.quotes.usd)?.toString() ?: ""
                vm.getHistoricalTickerData(false, ticker.id, chosenTimeFrame)
                    .observe(viewLifecycleOwner) { tickerData ->
                        makeLineChart(itemLineChart, tickerData)
                    }
            }
        }

        // SETUP LINE CHART //
        private fun makeLineChart(chart: LineChart, tickerData: List<HistoricalTicker>) {
            chart.setBackgroundColor(resources.getColor(R.color.white))
//            chart.setTouchEnabled(true)

            chart.setDrawGridBackground(false)

            // create marker to display box when values are selected
            // Set the marker to the chart
            linearMarker.chartView = chart
            chart.marker = linearMarker

            // enable scaling and dragging
//            chart.isDragEnabled = true
            chart.setScaleEnabled(false)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
//            chart.setPinchZoom(true)

            // add data
            setLinearData(chart, tickerData)
            // draw points over time
            chart.animateX(1500)
            // get the legend (only possible after setting data)
            val l: Legend = chart.legend
            // draw legend entries as lines
            l.form = Legend.LegendForm.LINE
        }

        private fun setLinearData(chart: LineChart, tickerData: List<HistoricalTicker>) {
            val set1: LineDataSet?
            val entries = ArrayList<Entry>()

            for (i in tickerData.indices) {
                entries.add(
                    Entry(i.toFloat(), tickerData[i].price.toFloat())
                )
            }

            if (chart.data != null && chart.data.dataSetCount > 0) {
                // If data has already been created.
                set1 = chart.data.getDataSetByIndex(0) as LineDataSet
                set1.values = entries
                set1.notifyDataSetChanged()
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
            } else {
                // create a dataset and give it a type
                set1 = LineDataSet(entries, "DataSet 1")
                set1.setDrawIcons(false)
                // draw dashed line
//                set1.enableDashedLine(10f, 5f, 0f)
                // black lines and points
                set1.color = Color.BLACK        // todo: change the color of the line
                // line thickness and point size
                set1.lineWidth = 1f     // todo: change the width of the line

//                set1.setCircleColor(Color.BLACK)
//                set1.circleRadius = 3f
//                set1.setDrawCircleHole(false)
                set1.setDrawCircles(false)

                // customize legend entry
                // todo: take out legend
//                set1.formLineWidth = 1f
//                set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
//                set1.formSize = 15f

                // todo: get rid of this text (for the value of the data in the line)
                // text size of values
                set1.valueTextSize = 9f

                // todo: take out the grids using this (probably)
                lateinit var xAxis: XAxis
                lateinit var yAxis: YAxis
                xAxis = chart.xAxis
//        xAxis.enableGridDashedLine(10f, 10f, 0f)
                yAxis = chart.axisLeft
//        // disable dual axis (only use LEFT axis)
                chart.axisRight.isEnabled = false
                chart.axisLeft.isEnabled = false
//        // horizontal grid lines
//        yAxis.enableGridDashedLine(10f, 10f, 0f)
//        // axis range
                xAxis.setDrawGridLines(false)
                yAxis.setDrawGridLines(false)



                val dataSets = ArrayList<ILineDataSet>()
                dataSets.add(set1) // add the data sets
                // create a data object with the data sets
                val data = LineData(dataSets)
                // set data
                chart.data = data
            }
        }
        // SETUP LINE CHART //

        companion object {
            fun from(
                viewLifecycleOwner: LifecycleOwner,
                chosenTimeFrame: DateFrame,
                resources: Resources,
                linearMarker: LinearMarker,
                vm: CryptoViewModel,
                parent: ViewGroup
            ): CoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinChartRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return CoinsViewHolder(
                    viewLifecycleOwner, chosenTimeFrame, resources,
                    linearMarker, vm, binding
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