package com.example.cryptomarket.ui.coins

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
import com.google.android.material.bottomsheet.BottomSheetBehavior

private const val TAG = "CoinsListFrag__TAG"

class CoinsListFragment : Fragment() {

    private var binding: FragmentCoinsListBinding? = null
    private val vm: CryptoViewModel by activityViewModels()
    private lateinit var coinsListAdapter: CoinsListAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCoinsListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinsListAdapter = CoinsListAdapter(viewLifecycleOwner, DateFrame.WEEK, vm)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            coinsListRecycler.adapter = coinsListAdapter
            coinsListRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        setObservers()
        setBottomSheetBehavior()
        timeFrameClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SETUP //
    private fun setBottomSheetBehavior() {
        binding?.apply {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
            bottomSheetBehavior.peekHeight = collapsedDataContainer.height
            val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            Log.d(TAG, "onStateChanged: STATE_EXPANDED")
                            toggleAppbar(false)
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            Log.d(TAG, "onStateChanged: STATE_COLLAPSED")
                            toggleAppbar(true)
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            Log.d(TAG, "onStateChanged: STATE_HIDDEN")
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            Log.d(TAG, "onStateChanged: STATE_DRAGGING")
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            Log.d(TAG, "onStateChanged: STATE_HALF_EXPANDED")
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            Log.d(TAG, "onStateChanged: STATE_SETTLING")
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    Log.d(TAG, "onSlide: ")
                }
            }
            bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
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
        }
        vm.tickers.observe(viewLifecycleOwner) {
            coinsListAdapter.submitList(it)
            setCoinOnSheet(it[0])
        }
    }
    // SETUP //

    // HELPERS
    private fun setCoinOnSheet(ticker: Ticker) {
        val percentChange1w = "1w: ${ticker.quotes.usd.percentChange7d}"
        val percentChange1m = "1m: ${ticker.quotes.usd.percentChange30d}"
        binding?.apply {
            tickerNameTxt.text = ticker.name
            sheetSymbolTxt.text = ticker.symbol
            totalSupplyTxt.text = ticker.totalSupply.toString()
            percentChange7dTxt.text = ticker.quotes.usd.percentChange7d.toString()

            tickerPriceTxt.text = ticker.quotes.usd.price.toString()
            percentChangeATxt.text = percentChange1w
            percentChangeBTxt.text = percentChange1m

            setMoreInfoDataToUI(ticker.quotes.usd, ticker.id)
        }
    }

    private fun timeFrameClickListeners() {
        binding?.apply {
            timeframeBtnGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        dBtn.id -> populateCharts(vm.getHistoricalTickerData(DateFrame.DAY))
                        wBtn.id -> vm.getHistoricalTickerData(DateFrame.WEEK)
                        mBtn.id -> vm.getHistoricalTickerData(DateFrame.MONTH)
                        qBtn.id -> vm.getHistoricalTickerData(DateFrame.QUARTER)
                        yBtn.id -> vm.getHistoricalTickerData(DateFrame.YEAR)
                    }
                } else wBtn.isChecked = true
            }
        }
    }

    private fun setMoreInfoDataToUI(priceData: PriceData, coinID: String) {
        vm.getCoinData(coinID).observe(viewLifecycleOwner) {
            binding?.apply {
                rankTxt.text = it.rank.toString()
                typeTxt.text = it.type ?: ""
                teamTxt.text = displayTeam(it.team)
                descriptionTxt.text = it.description ?: ""
                openSourceTxt.text = displayIsOpenSource(it.openSource)
                startedAtTxt.text = displayStartedAt(it.startedAt)
                proofTypeTxt.text = it.proofType ?: ""
                orgStructureTxt.text = it.orgStructure ?: ""
                hashAlgorithmTxt.text = it.hashAlgorithm ?: ""
                athPriceTxt.text = displayATHPrice(priceData.athPrice)
                athDateTxt.text = displayATHDate(priceData.athDate)
            }
        }
    }

    private fun populateCharts(tickerData : LiveData<List<HistoricalTicker>>) {
        binding?.apply {
            tickerData.observe(viewLifecycleOwner) {
                tickerChartCollapsedTxt.text = it.toString()
                tickerChartExpandedTxt.text = it.toString()
            }
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
    // HELPERS
}