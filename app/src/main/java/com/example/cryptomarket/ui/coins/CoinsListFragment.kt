package com.example.cryptomarket.ui.coins

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.R
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.FragmentCoinsListBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen
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
        vm.startApplication()
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
            bottomSheetBehavior.peekHeight = 240    // todo: bottomSheetView.height
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
            when(it) {
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
        // todo: ticker ticker data to the UI
        resources.getColor(R.color.black)
        val percentChange1w = "1w: ${ticker.quotes[0].percentChange7d}"
        val percentChange1m = "1m: ${ticker.quotes[0].percentChange30d}"
        val rankString = "Rank: ${ticker.rank}"

        binding?.apply {
            tickerNameTxt.text = ticker.name
            sheetSymbolTxt.text = ticker.symbol
            totalSupplyTxt.text = ticker.totalSupply.toString()
            percentChange7dTxt.text = ticker.quotes[0].percentChange7d.toString()

            // todo: probably have a vm. variable containing chart data, and it changes when the timeframebtns are clicked
            tickerChartCollapsedTxt.text = ;    // todo: use the data from the query for tickerChartExpandedTxt (right below)
            tickerChartExpandedTxt.text = ;     // todo: do a query to get the historical ticker data.

            tickerPriceTxt.text = ticker.quotes[0].price.toString()
            percentChangeATxt.text = percentChange1w
            percentChangeBTxt.text = percentChange1m

            setMoreInfoDataToUI()
        }
    }
    private fun timeFrameClickListeners() {
        // todo: change the color of txt btns after click
        binding?.apply {
            // todo: set click listeners no time frame btns
            dTxt
            wTxt
            mTxt
            qTxt
            yTxt
        }
    }
    private fun setMoreInfoDataToUI() {
        // todo: do a query to get CoinData data class
        binding?.apply {
            rankTxt.text
            typeTxt.text
            teamTxt.text
            descriptionTxt.text
            openSourceTxt.text
            startedAtTxt.text
            proofTypeTxt.text
            orgStructureTxt.text
            hashAlgorithmTxt.text
            athPriceTxt.text
            athDateTxt.text
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