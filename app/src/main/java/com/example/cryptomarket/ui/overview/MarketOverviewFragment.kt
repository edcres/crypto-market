package com.example.cryptomarket.ui.overview

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.github.mikephil.charting.components.Legend
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
                val top10Tickers = it.take(10) // todo: check if these coins are ordered by rank
                // todo: figure out what else to do when using you start using charts.
                //      do in vm and background thread
                // list of Ticker.PriceData.market_cap of the top 10 coins
                marketCapShareTxt.text = top10Tickers.toString()
                // Ticker.PriceData.volume24h
                volume24hTxt.text = top10Tickers.toString()
                // Ticker.TotalSupply
                totalSupplyTxt.text = top10Tickers.toString()

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
                pieChart.setHoleColor(Color.WHITE)
                pieChart.setTransparentCircleColor(Color.WHITE)
//                pieChart.setTransparentCircleAlpha(110)
                pieChart.setTransparentCircleAlpha(0)
//                pieChart.holeRadius = 58f
                pieChart.holeRadius = 50f
                pieChart.transparentCircleRadius = 61f

                pieChart.setDrawCenterText(true)
                pieChart.rotationAngle = 0f
                pieChart.isRotationEnabled = true
                pieChart.isHighlightPerTapEnabled = true
//            pieChart.setOnChartValueSelectedListener(this)
                pieChart.animateY(1400, Easing.EaseInOutQuad)

//                val l: Legend = pieChart.legend
//                l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//                l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//                l.orientation = Legend.LegendOrientation.VERTICAL
//                l.setDrawInside(false)
//                l.xEntrySpace = 7f
//                l.yEntrySpace = 0f
//                l.yOffset = 0f

                pieChart.setEntryLabelColor(Color.WHITE)
                pieChart.setEntryLabelTextSize(12f)

                // todo: set the items and the number of items for that pie
                setPiesData(top10Tickers, pieChart, 14f)
            }
        }
    }

    // todo: edit all of this class, and maybe don't even pass the values as parameters
    private fun setPiesData(
        top10Tickers: List<Ticker>, pieChart: PieChart, range: Float
    ) {
        val entries = ArrayList<PieEntry>()
        for (i in top10Tickers.indices) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    when (pieChart.id) {
                        binding!!.marketCapPie.id -> top10Tickers[i % top10Tickers.size].quotes.usd
                            .marketCap
                        binding!!.volume24hPie.id -> top10Tickers[i % top10Tickers.size].quotes.usd
                            .volume24h
                        binding!!.totalSupplyPie.id -> top10Tickers[i % top10Tickers.size]
                            .totalSupply
                        else -> 0.0
                    }
//                    resources.getDrawable(R.drawable.star)
                )
            )
        }

        // todo: change the data set to the crypto data
        val dataSet = PieDataSet(entries, "Election Results")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
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

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //        data.setValueTypeface(tfLight)
        pieChart.data = data

        // undo all highlights
        pieChart.highlightValues(null)
        pieChart.invalidate()
    }

    // for pie chart
    // todo: change what this says
    private fun generateCenterSpannableText(chart: PieChart): SpannableString? {
        val s = when (chart.id) {
            binding!!.marketCapPie.id -> SpannableString("Market Cap")
            binding!!.volume24hPie.id -> SpannableString("Volume\n24 Hour")
            binding!!.totalSupplyPie.id -> SpannableString("Total Supply")
            else -> SpannableString("")
        }
//        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
//        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
//        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
//        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
//        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
//        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }
    // MAKE CHARTS //
}