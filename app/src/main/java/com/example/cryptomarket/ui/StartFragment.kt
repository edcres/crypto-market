package com.example.cryptomarket.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.cryptomarket.R
import com.example.cryptomarket.utils.FragChosen
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val TAG = "StartFrag__TAG"

class StartFragment : Fragment() {

    private val vm: CryptoViewModel by activityViewModels()
    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Bottom navigation
//        bottomNavBar = view.findViewById(R.id.bottom_navigation)
//        val navHostFragment = requireActivity().supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
//        if (navHostFragment != null) {
//            NavigationUI.setupWithNavController(bottomNavBar, navHostFragment.navController)
//        } else Log.e(TAG, "onCreate: navHostFragment is null")


//        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController

//        view.findViewById<FragmentContainerView>(R.id.nav_host_fragment).contro
//        val navController = Navigation.findNavController(view.findViewById(R.id.nav_host_fragment))
//        val navController = NavHostFragment.findNavController(this)
//        val navController = findNavController()
//        val navController = Navigation.findNavController(requireParentFragment().requireView())
//        val navController = Navigation.findNavController(requireView(), R.id.nav_host_fragment)
//        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.coins
        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.market -> {
                    vm.setFragChosen(FragChosen.MARKET)
                    true
                }
                R.id.coins -> {
                    vm.setFragChosen(FragChosen.COINS)
                    true
                }
                R.id.news -> {
                    vm.setFragChosen(FragChosen.NEWS)
                    true
                }
                else -> false
            }
        }
    }


//    override fun onResume() {
//        super.onResume()
//        val navController = findNavController()
//    }
}