package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - data to view
 *      - News
 *          - API queries
 *          - Repo
 *          - In View
 *      - Coins
 *          - API queries
 *          - Repo
 *          - In View
 *      - Market Overview
 *          - API queries
 *          - Repo
 *          - In View
 * - Get rid of the dummy
 *      - .xml
 *      - data class
 *
 * - Repo
 *
 * - configure bottom sheets for the API data
 *      - overview data in views
 *      - coins list
 *          - recyclerView for coins list
 *      - news
 *          - recyclerView for news list
 *
 * - try catch (error handling in general)
 *      - overall market
 *      - news
 *      - coins list
 *
 * AFTER:
 * - put API data in charts
 *      - customize the charts
 * - decide what more I want to display in market overview
 * - colors dark mode
 * - app icon
 * - colors light mode
 */

/** maybe:
 * - local database
 * - Consider including OHLC
 *      - https://api.coinpaprika.com/#tag/Coins/paths/~1coins~1{coin_id}~1ohlcv~1latest~1/get
 * - list of exchanged
 * - price converter
 * - news article link navigate to the website.
 */

/**
 * check todos
 * check warnings
 * Comment code
 * remove unused imports
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
        }
    }
}