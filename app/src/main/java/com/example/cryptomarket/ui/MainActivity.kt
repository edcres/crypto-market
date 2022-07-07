package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - News
 *      - API
 *          - api service file
 *                  - https://cryptopanic.com/api/v1/posts/?auth_token=03cdeeb873984d7cf7a0723c49cb22676fcd56f6&kind=news
 *          - Data classes
 *      - API key
 *          - to local host
 *          - to API service
 *
 * - data/backend stuff
 *      - News
 *          - Data class
 *          - API queries
 *          - recyclerView for news list
 *      - Coins
 *          - Data class
 *          - API queries
 *          - recyclerView for coins list
 *          - try catch (error handling in general)
 *      - Market Overview
 *          - Data class
 *          - API queries
 *          - overview data in views
 *          - try catch (error handling in general)
 * - Get rid of the dummy
 *      - .xml
 *      - data class
 *
 * - configure bottom sheets for the API data
 *      - coins list
 *      - news
 *
 * AFTER:
 * - put API data in charts
 *      - customize the charts
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