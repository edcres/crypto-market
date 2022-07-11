package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

// ATH = all time high

/** todo:
 * - coins bottom sheet
 *      - set ticker data to UI
 *          - everything else
 *          - chart data
 *          - delete unused drawables
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
 * - user can choose if the interval is hr, week, month...
 * - news article link navigate to the website. When click on news recycler item
 */

/** maybe:
 * - local database
 * - Consider including OHLC
 *      - https://api.coinpaprika.com/#tag/Coins/paths/~1coins~1{coin_id}~1ohlcv~1latest~1/get
 * - list of exchanged
 * - price converter
 * - calculate percent change from user click in chart, to current price (I love this feature)
 * - add rank to bottom sheet info
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