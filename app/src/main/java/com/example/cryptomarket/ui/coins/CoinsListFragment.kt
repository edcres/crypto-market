package com.example.cryptomarket.ui.coins

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.PriceData
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.FragmentCoinsListBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetBehavior

private const val TAG = "CoinsListFrag__TAG"

class CoinsListFragment : Fragment() {

    private var binding: FragmentCoinsListBinding? = null
    private val vm: CryptoViewModel by activityViewModels()
    private lateinit var coinsListAdapter: CoinsListAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var chartMarker: ChartMarker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCoinsListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartMarker = ChartMarker(requireContext(), R.layout.line_chart_marker)
        coinsListAdapter =
            CoinsListAdapter(viewLifecycleOwner, DateFrame.MONTH, resources, chartMarker, vm)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            coinsListRecycler.adapter = coinsListAdapter
            coinsListRecycler.layoutManager = LinearLayoutManager(requireContext())
            minimizeBtn.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            expandedSheetMainContainer.setOnClickListener {
                // The purpose of this click listener is to cover
                //  up a bug clicking list items behind the sheet.
            }
        }
        setObservers()
        setBottomSheetBehavior()
    }

    override fun onResume() {
        super.onResume()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SETUP //
    private fun setBottomSheetBehavior() {
        binding?.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
            bottomSheetBehavior.peekHeight =
                230  // Should be the same height as collapsedDataContainer
            val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                @SuppressLint("SwitchIntDef")
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            toggleAppbar(false)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            toggleAppbar(true)
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            Log.i(TAG, "onStateChanged: STATE_DRAGGING")
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            if (collapsedDataContainer.visibility == View.VISIBLE)
                                collapsedDataContainer.visibility = View.INVISIBLE
                            if (appBarLayout.visibility == View.VISIBLE)
                                appBarLayout.visibility = View.INVISIBLE
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }
            bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
            collapsedDataContainer.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun setObservers() {
        vm.fragChosen.observe(viewLifecycleOwner) {
            val navController = findNavController()
            when (it) {
                FragChosen.MARKET -> navController.navigate(R.id.action_coins_to_market)
                FragChosen.NEWS -> navController.navigate(R.id.action_coins_to_news)
                else -> Log.i(TAG, "setObservers: from Coins to $it")
            }
        }
        vm.tickerClicked.observe(viewLifecycleOwner) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            setCoinOnSheet(it)
            timeFrameClickListeners(it.id)
        }
        vm.tickers.observe(viewLifecycleOwner) {
            coinsListAdapter.submitList(it)
            setCoinOnSheet(it[0])
            timeFrameClickListeners(it[0].id)
            populateCharts(vm.getHistoricalTickerData(true, it[0].id, DateFrame.MONTH))
        }
    }
    // SETUP //

    // HELPERS
    @SuppressLint("SetTextI18n")
    private fun setCoinOnSheet(ticker: Ticker) {
        binding?.apply {
            tickerSymbolTxt.text = ticker.symbol
            collapsedSymbolTxt.text = ticker.symbol
            collapsedPriceTxt.text = presentPriceFormatUSD("", ticker.quotes.usd.price)
            tickerPriceTxt.text = presentPriceFormatUSD("", ticker.quotes.usd.price)
            setTimeFrameText(
                DateFrame.MONTH, ticker.quotes.usd.percentChange30d ?: 0.0, ticker
            )
            setMoreInfoDataToUI(ticker.quotes.usd, ticker.id)
        }
    }

    private fun displayPercentChangeColor(percentChangeTxt: TextView, percentChange: Double) {
        if (percentChange > 0.0) {
            percentChangeTxt.setTextColor(resources.getColor(R.color.positive_green))
        } else if (percentChange < 0.0) {
            percentChangeTxt.setTextColor(resources.getColor(R.color.negative_red))
        }
    }

    private fun timeFrameClickListeners(tickerID: String) {
        binding?.apply {
            mBtn.isChecked = true
            val ticker = vm.tickerClicked.value ?: vm.tickers.value!![0]
            timeframeBtnGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        wBtn.id -> {
                            setTimeFrameText(
                                DateFrame.WEEK,
                                ticker.quotes.usd.percentChange7d,
                                ticker
                            )
                            populateCharts(
                                vm.getHistoricalTickerData(true, tickerID, DateFrame.WEEK)
                            )
                        }
                        mBtn.id -> {
                            setTimeFrameText(
                                DateFrame.MONTH,
                                ticker.quotes.usd.percentChange30d,
                                ticker
                            )
                            populateCharts(
                                vm.getHistoricalTickerData(true, tickerID, DateFrame.MONTH)
                            )
                        }
                        qBtn.id -> {
                            setTimeFrameText(DateFrame.QUARTER, null, ticker)
                            populateCharts(
                                vm.getHistoricalTickerData(true, tickerID, DateFrame.QUARTER)
                            )
                        }
                        sixMBtn.id -> {
                            setTimeFrameText(DateFrame.HALF_YEAR, null, ticker)
                            populateCharts(
                                vm.getHistoricalTickerData(true, tickerID, DateFrame.HALF_YEAR)
                            )
                        }
                        yBtn.id -> {
                            setTimeFrameText(
                                DateFrame.YEAR,
                                ticker.quotes.usd.percentChange1y,
                                ticker
                            )
                            populateCharts(
                                vm.getHistoricalTickerData(true, tickerID, DateFrame.YEAR)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setMoreInfoDataToUI(priceData: PriceData, coinID: String) {
        val notAvailable = "not available"
        vm.getCoinData(coinID).observe(viewLifecycleOwner) {
            binding?.apply {
                rankTxt.text = it.rank.toString()
                coinName2Txt.text = it.name
                typeTxt.text = it.type?.replaceFirstChar { it.titlecase() } ?: "Type $notAvailable"
                teamTxt.text = displayTeam(it.team)
                descriptionTxt.text = it.description ?: "Description $notAvailable"
                openSourceTxt.text = displayIsOpenSource(it.openSource)
                startedAtTxt.text = displayMoreInfo("Started at", reformatDate(it.startedAt))
                proofTypeTxt.text = it.proofType ?: "Proof type $notAvailable"
                orgStructureTxt.text = it.orgStructure ?: "Org structure $notAvailable"
                hashAlgorithmTxt.text = displayHashAlgorithm(it.hashAlgorithm)
                athPriceTxt.text = presentPriceFormatUSD("- ", priceData.athPrice)
                athDateTxt.text = displayMoreInfo("-", reformatDate(priceData.athDate))
            }
        }
    }

    private fun populateCharts(tickerData: LiveData<List<HistoricalTicker>>) {
        binding?.apply {
            tickerData.observe(viewLifecycleOwner) {
                makeCollapsedChart(tickerChartCollapsed, it)
                makeExpandedChart(tickerChartExpanded, it)
            }
        }
    }

    private fun makeCollapsedChart(chart: LineChart, tickerData: List<HistoricalTicker>) {
        chart.setBackgroundColor(resources.getColor(R.color.white))
        chart.setTouchEnabled(false)
        chart.setDrawGridBackground(false)
        // Create marker to display box when values are selected
        // Set the marker to the chart
        chartMarker.chartView = chart
        chart.marker = chartMarker
        chart.setBackgroundColor(resources.getColor(R.color.sheet_background))
        // Add data to chart
        setCollapsedChartData(chart, tickerData)
        // Draw points over time animation
        chart.animateX(650)
        // get the legend (only possible after setting data)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
    }

    private fun makeExpandedChart(chart: LineChart, tickerData: List<HistoricalTicker>) {
        chart.setBackgroundColor(resources.getColor(R.color.sheet_background))
        chart.setDrawGridBackground(false)
        // Create marker to display box when values are selected
        chartMarker.chartView = chart
        chart.marker = chartMarker
        chart.setScaleEnabled(false)
        val y = chart.axisLeft
//        y.setLabelCount(4, false)
        y.textColor = Color.GRAY
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        val x = chart.xAxis
        x.textColor = Color.GRAY
        x.isEnabled = false
        // Add data
        setExpandedChartData(chart, tickerData)
        chart.animateX(1000)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
    }

    private fun setCollapsedChartData(chart: LineChart, tickerData: List<HistoricalTicker>) {
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
//            set1.valueTextSize = 9f
//            set1.valueTextColor = Color.WHITE
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

    private fun setExpandedChartData(chart: LineChart, tickerData: List<HistoricalTicker>) {
        val set1: LineDataSet?
        val entries = ArrayList<Entry>()
        for (i in tickerData.indices) {
            entries.add(Entry(i.toFloat(), tickerData[i].price.toFloat()))
        }
        if (chart.data != null && chart.data.dataSetCount > 0) {
            // If data has already been created.
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = entries
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // Create a dataset and give it a type
            set1 = LineDataSet(entries, "DataSet 1")
            set1.setDrawIcons(false)
            set1.color = resources.getColor(R.color.chart_line_color)
//            set1.color = Color.BLACK
            set1.lineWidth = 1f
            set1.setDrawCircles(false)
            set1.setDrawValues(false)

            set1.highLightColor = resources.getColor(R.color.chart_line_color)
            set1.highlightLineWidth = 2f
            set1.setDrawHorizontalHighlightIndicator(false)

            // Fill color under data set line
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_chart_fill)
            set1.fillDrawable = drawable

            chart.xAxis.setDrawGridLines(false)
            chart.axisRight.isEnabled = false
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            // Create a data object with the data sets
            val data = LineData(dataSets)
            chart.data = data
        }
    }

    private fun toggleAppbar(hideAppbar: Boolean) {
        binding!!.apply {
            if (hideAppbar) {
                appBarLayout.visibility = View.GONE
                collapsedDataContainer.visibility = View.VISIBLE
            } else {
                collapsedDataContainer.visibility = View.GONE
                appBarLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setTimeFrameText(timeFrame: DateFrame, percentChange: Double?, ticker: Ticker) {
        binding?.apply {
            if (percentChange != null) {
                // Collapsed Sheet
                percentChangeCollapsedTxt.text = displayPercent("", percentChange)
                displayPercentChangeColor(percentChangeCollapsedTxt, percentChange)
                timeFrameTxt.text = timeFrame.abbrev
                percentChangeCollapsedTxt.visibility = View.VISIBLE
                // Expanded Sheet
                frameChangeBTxt.text = timeFrame.abbrev
                percentChangeBTxt.text = displayPercent("", percentChange)
                displayPercentChangeColor(percentChangeBTxt, percentChange)
                percentChangeBTxt.visibility = View.VISIBLE
            } else {
                // Collapsed Sheet
                timeFrameTxt.text = timeFrame.abbrev
                percentChangeCollapsedTxt.visibility = View.GONE
                // Expanded Sheet
                frameChangeBTxt.text = timeFrame.abbrev
                percentChangeBTxt.visibility = View.INVISIBLE
            }
            // todo: Do this for the list items too
            frameChangeATxt.text = DateFrame.WEEK.abbrev
            percentChangeATxt.text =
                displayPercent("", ticker.quotes.usd.percentChange7d ?: 0.0)
            displayPercentChangeColor(
                percentChangeATxt, ticker.quotes.usd.percentChange7d ?: 0.0
            )
        }
    }
    // HELPERS
}