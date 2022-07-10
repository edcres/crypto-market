package com.example.cryptomarket.ui.overview

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
import com.example.cryptomarket.databinding.FragmentNewsBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.FragChosen

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
            when(it) {
                FragChosen.COINS -> navController.navigate(R.id.action_market_to_coins)
                FragChosen.NEWS -> navController.navigate(R.id.action_market_to_news)
                else -> Log.i(TAG, "setObservers: from Market to $it")
            }
        }
        vm.globalData.observe(viewLifecycleOwner) {
            populateView(it)
        }
        vm.tickers.observe(viewLifecycleOwner) {
            // todo: get the top 10 coins (by rank)
            binding?.apply {
                val top10 = it.take(10)
                // todo: figure out what else to do when using you start using charts.
                //      do in vm and background thread
                marketCapShareTxt.text = top10.toString()  // list of PriceData.market_cap of the top 10 coins
                volume24hTxt.text = top10.toString()       // PriceData.volume24h
                totalSupplyTxt.text = top10.toString()    // Ticker.TotalSupply
            }
        }
    }
    private fun populateView(globalData: GlobalData) {
        binding?.apply {
            marketCapTxt.text = globalData.marketCapUsd.toString()
            volume24hrUsdTxt.text = globalData.volume24hUsd.toString()
            bitcoinDominancePercentageTxt.text = globalData.bitcoinDominancePercentage.toString()
            marketCapAthValueTxt.text = globalData.marketCapAthValue.toString()
            marketCapAthDateTxt.text = globalData.marketCapAthDate
        }
    }
    // SETUP //
}