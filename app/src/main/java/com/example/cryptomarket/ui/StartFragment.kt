package com.example.cryptomarket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.cryptomarket.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

private const val TAG = "StartFrag__TAG"

class StartFragment : Fragment() {

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


        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.coins
        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId) {
                R.id.market -> {

                    true
                }
                R.id.coins -> {
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.news -> {
                    true
                }
                else -> false
            }
        })

//        view.findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemReselectedListener { item ->
//            when(item.itemId) {
//                R.id.item1 -> {
//                    // Respond to navigation item 1 reselection
//                }
//                R.id.item2 -> {
//                    // Respond to navigation item 2 reselection
//                }
//            }
//        }
    }
}