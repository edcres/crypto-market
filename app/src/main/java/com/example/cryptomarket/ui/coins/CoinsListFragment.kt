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
import com.example.cryptomarket.databinding.FragmentCoinsListBinding
import com.example.cryptomarket.ui.CryptoViewModel
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
        coinsListAdapter = CoinsListAdapter()
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            coinsListRecycler.adapter = coinsListAdapter
            coinsListRecycler.layoutManager = LinearLayoutManager(requireContext())
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
            // todo: get rid of this
            coinsListHeader.setOnClickListener {
                if(bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED;
                }
                else {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;
                }
//                val bottomSheet = CoinsBottomSheetFragment()
//                bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
            }
        }
        vm.startApplication()
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SETUP //
    private fun setBottomSheet() {

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
    }
    // SETUP //
}