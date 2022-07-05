package com.example.cryptomarket.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptomarket.R
import com.example.cryptomarket.utils.FragChosen
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "StartFrag__TAG"

class StartFragment : Fragment() {

    private val vm: CryptoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.market -> {
                    if (vm.fragChosen.value != FragChosen.MARKET) vm.setFragChosen(FragChosen.MARKET)
                    true
                }
                R.id.coins -> {
                    if (vm.fragChosen.value != FragChosen.COINS) vm.setFragChosen(FragChosen.COINS)
                    true
                }
                R.id.news -> {
                    if (vm.fragChosen.value != FragChosen.NEWS) vm.setFragChosen(FragChosen.NEWS)
                    true
                }
                else -> false
            }
        }
    }
}