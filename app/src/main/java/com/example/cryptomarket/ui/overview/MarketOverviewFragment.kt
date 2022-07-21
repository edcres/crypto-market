package com.example.cryptomarket.ui.overview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.databinding.FragmentMarketOverviewBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

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
                val top10 = it.take(10) // todo: check if these coins are ordered by rank
                // todo: figure out what else to do when using you start using charts.
                //      do in vm and background thread
                marketCapShareTxt.text =
                    top10.toString()  // list of Ticker.PriceData.market_cap of the top 10 coins
                volume24hTxt.text = top10.toString()       // Ticker.PriceData.volume24h
                totalSupplyTxt.text = top10.toString()    // Ticker.TotalSupply
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
    private fun makePieCharts() {
        binding?.apply {
            val piesToMake = listOf(marketCapPie, volume24hPie, totalSupplyPie)
            for (pieChart in piesToMake) {

                pieChart.setUsePercentValues(true)
                pieChart.description.isEnabled = true
                pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
                pieChart.dragDecelerationFrictionCoef = 0.95f
//            pieChart.centerText = generateCenterSpannableText()

                pieChart.isDrawHoleEnabled = true
                pieChart.setHoleColor(Color.WHITE)
                pieChart.setTransparentCircleColor(Color.WHITE)
                pieChart.setTransparentCircleAlpha(110)
                pieChart.holeRadius = 58f
                pieChart.transparentCircleRadius = 61f

                pieChart.setDrawCenterText(true)
                pieChart.rotationAngle = 0f
                pieChart.isRotationEnabled = true
                pieChart.isHighlightPerTapEnabled = true
//            pieChart.setOnChartValueSelectedListener(this)
                pieChart.animateY(1400, Easing.EaseInOutQuad)

                val l: Legend = pieChart.legend
                l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                l.orientation = Legend.LegendOrientation.VERTICAL
                l.setDrawInside(false)
                l.xEntrySpace = 7f
                l.yEntrySpace = 0f
                l.yOffset = 0f

                pieChart.setEntryLabelColor(Color.WHITE)
                pieChart.setEntryLabelTextSize(12f)

                setPiesData(pieChart, 6, 14f)
            }
        }
    }

    private fun setPiesData(pieChart: PieChart, count: Int, range: Float) {
        
    }
    // MAKE CHARTS //
}