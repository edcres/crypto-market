package com.example.cryptomarket.ui.overview

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.FragmentMarketOverviewBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF

private const val TAG = "MarketListFrag__TAG"

class MarketOverviewFragment : Fragment() {

    private var binding: FragmentMarketOverviewBinding? = null
    private val vm: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentMarketOverviewBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SETUP //
    private fun setObservers() {
        vm.fragChosen.observe(viewLifecycleOwner) {
            val navController = findNavController()
            when (it) {
                FragChosen.COINS -> navController.navigate(R.id.action_market_to_coins)
                FragChosen.NEWS -> navController.navigate(R.id.action_market_to_news)
                else -> Log.i(TAG, "setObservers: from Market to $it")
            }
        }
        vm.globalData.observe(viewLifecycleOwner) {
            populateView(it)
        }
        vm.tickers.observe(viewLifecycleOwner) {
            binding?.apply {
                val top10Tickers = it.take(10)
                makePieCharts(top10Tickers)
            }
        }
    }

    private fun populateView(globalData: GlobalData) {
        val marketCapATHDateString = reformatDate(globalData.marketCapAthDate)
        binding?.apply {
            marketCapTxt.text =
                removeTrailing2Zeros(presentPriceFormatUSD("", globalData.marketCapUsd))
            volume24hrUsdTxt.text =
                removeTrailing2Zeros(presentPriceFormatUSD("", globalData.volume24hUsd))
            bitcoinDominancePercentageTxt.text =
                displayPercent("", globalData.bitcoinDominancePercentage)
            marketCapAthValueTxt.text =
                removeTrailing2Zeros(presentPriceFormatUSD("", globalData.marketCapAthValue))
            marketCapAthDateTxt.text = marketCapATHDateString
        }
    }
    // SETUP //

    // MAKE CHARTS //
    private fun makePieCharts(top10Tickers: List<Ticker>) {
        binding?.apply {
            val piesToMake = listOf(marketCapPie, volume24hPie, totalSupplyPie)
            for (pieChart in piesToMake) {

                pieChart.setUsePercentValues(true)
                pieChart.description.isEnabled = false
                pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
                pieChart.dragDecelerationFrictionCoef = 0.85f
                pieChart.centerText = generateCenterSpannableText(pieChart)

                pieChart.isDrawHoleEnabled = true
                pieChart.setHoleColor(resources.getColor(R.color.pie_hole))
                pieChart.holeRadius = 50f
                pieChart.setTransparentCircleColor(resources.getColor(R.color.pies_inner_circle))
//                pieChart.setTransparentCircleAlpha(0)
//                pieChart.transparentCircleRadius = 62f

                pieChart.setDrawCenterText(true)
//                pieChart.setCenterTextColor(resources.getColor(R.color.pies_labels))
//                pieChart.setCenterTextColor(Color.WHITE)
                pieChart.rotationAngle = 0f
                pieChart.isRotationEnabled = true
                pieChart.isHighlightPerTapEnabled = true
//                pieChart.setOnChartValueSelectedListener(this)
                pieChart.animateY(1400, Easing.EaseInOutQuad)

                pieChart.setEntryLabelColor(resources.getColor(R.color.pies_labels))
                pieChart.setEntryLabelTextSize(12f)

                pieChart.legend.isEnabled = false
                setPiesData(top10Tickers, pieChart)
            }
        }
    }

    // todo: probably send this to the vm
    private fun setPiesData(top10Tickers: List<Ticker>, pieChart: PieChart) {
        val entries = ArrayList<PieEntry>()
        for (i in top10Tickers.indices) {
            // todo: what is (i % top10Tickers.size)?
            val thisTicker = top10Tickers[i % top10Tickers.size]
            binding?.apply {
                entries.add(
                    when (pieChart.id) {
                        // Market Cap chart
                        marketCapPie.id ->
                            PieEntry(thisTicker.quotes.usd.marketCap.toFloat(), thisTicker.symbol)
                        // 24h Volume chart
                        volume24hPie.id ->
                            PieEntry(thisTicker.quotes.usd.volume24h.toFloat(), thisTicker.symbol)
                        // Total Supply chart
                        totalSupplyPie.id ->
                            PieEntry(thisTicker.totalSupply.toFloat(), thisTicker.symbol)
                        else -> PieEntry(0f, "");
                    }
                )
            }
        }
        val dataSet = PieDataSet(entries, "Top 10 Coins")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 1f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        // todo: add custom colors
        val colors = java.util.ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(resources.getColor(R.color.pies_labels))
        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }

    // for pie chart
    private fun generateCenterSpannableText(chart: PieChart): SpannableString? {
        val string = when (chart.id) {
            binding!!.marketCapPie.id -> SpannableString("Market Cap")
            binding!!.volume24hPie.id -> SpannableString("Volume\n24 Hours")
            binding!!.totalSupplyPie.id -> SpannableString("Total Supply")
            else -> SpannableString("")
        }
        string.setSpan(RelativeSizeSpan(1.5f), 0, string.length, 0)
        string.setSpan(StyleSpan(Typeface.BOLD), 0, string.length, 0)
        string.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.pies_labels)),
            0, string.length, 0
        )
        return string
    }
    // MAKE CHARTS //
}