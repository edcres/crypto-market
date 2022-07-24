package com.example.cryptomarket.ui.coins

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
import com.github.mikephil.charting.components.LimitLine

class CoinsListAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val chosenTimeFrame: DateFrame,
    private val resources: Resources,
    private val vm: CryptoViewModel
) :
    ListAdapter<Ticker, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder.from(viewLifecycleOwner, chosenTimeFrame, resources, vm, parent)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CoinsViewHolder private constructor(
        private val viewLifecycleOwner: LifecycleOwner,
        private val chosenTimeFrame: DateFrame,
        private val resources: Resources,
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
                percentChangeTxt.text = pickPercentChange(chosenTimeFrame, ticker.quotes.usd)?.toString() ?: ""
                vm.getHistoricalTickerData(false, ticker.id, chosenTimeFrame)
                    .observe(viewLifecycleOwner) { tickerData ->
                        makePieChart(itemLineChart, tickerData)
                    }
            }
        }

        // SETUP LINE CHART //
        private fun makePieChart(chart: LineChart, tickerData: List<HistoricalTicker>) {
            chart.setBackgroundColor(resources.getColor(R.color.white))
//        lineChart.description.isEnabled = false
            chart.setTouchEnabled(true)

            chart.setOnChartValueSelectedListener(this)
            chart.setDrawGridBackground(false)

            // create marker to display box when values are selected
            val mv = LinearMarker(requireContext(), R.layout.custom_marker_view)
            // Set the marker to the chart
            mv.chartView = chart
            chart.marker = mv

            // enable scaling and dragging
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true)

            xAxis = chart.xAxis
            xAxis.enableGridDashedLine(10f, 10f, 0f)

            yAxis = chart.axisLeft
            // disable dual axis (only use LEFT axis)
            chart.axisRight.isEnabled = false
            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)
            // axis range
            yAxis.axisMaximum = 200f
            yAxis.axisMinimum = -50f

            // Create Limit Lines
            llXAxis = LimitLine(9f, "Index 10")
            llXAxis.lineWidth = 4f
            llXAxis.enableDashedLine(10f, 10f, 0f)
            llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            llXAxis.textSize = 10f
//        llXAxis.typeface = tfRegular
            ll1 = LimitLine(150f, "Upper Limit")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 10f
//        ll1.typeface = tfRegular
            ll2 = LimitLine(-30f, "Lower Limit")
            ll2.lineWidth = 4f
            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 10f
//        ll2.typeface = tfRegular
            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true)
            xAxis.setDrawLimitLinesBehindData(true)
            // add limit lines
            yAxis.addLimitLine(ll1)
            yAxis.addLimitLine(ll2)
            //xAxis.addLimitLine(llXAxis);

            // add data
            setLinearData(45, 180f)
            // draw points over time
            chart.animateX(1500)
            // get the legend (only possible after setting data)
            val l: Legend = chart.legend
            // draw legend entries as lines
            l.form = Legend.LegendForm.LINE
        }
        private fun setPiesData() {
        }
        // SETUP LINE CHART //

        companion object {
            fun from(
                viewLifecycleOwner: LifecycleOwner,
                chosenTimeFrame: DateFrame,
                resources: Resources,
                vm: CryptoViewModel,
                parent: ViewGroup
            ): CoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinChartRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return CoinsViewHolder(viewLifecycleOwner, chosenTimeFrame, resources, vm, binding)
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