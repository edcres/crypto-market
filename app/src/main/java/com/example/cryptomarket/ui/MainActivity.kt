package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

// ATH = all time high
/** todo:
 * - fix some UI
 *      - coins
 *          - display Ticker data on the bottom sheet
 *          - bottom sheet displaying chart when clicking the time frame btns
 * - bugs
 * - remove logs
 *
 * - try catch (error handling in general)
 *      - test for a bad connection
 *          - at startup
 *          - when the connection goes out at runtime
 *      - overall market
 *      - news
 *      - coins list
 *
 * display moreInfo better (maybe say the info is unavailable) (also, empty list still shows [])
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
 * - maybe put more logic in the viewModel
 */

/** bugs:
 * - hide minimised chart when opening bottom sheet
 * - bottom sheet gets disappeared and not minimized
 * - the top right of the bottom sheet does not have rounded corners
 * - when btn is not picked at first (the but is set to selected in the xml).
 *      - when w btn is picked, it can't be un-clicked
 * - start fragment is created twice at startup. Maybe other views too
 * - Some charts are not loaded within a good amount of time, idk what triggers eventually
 *      - put the queries that make it crash in a queue to be don later and store the in the vm map (I don't need this put it's cool)
 *          - maybe override onViewRecycled(on the adapter)
 *          - maybe a map <id, request>
 *          - maybe there's something in retrofit or another library that queues requests
 * When clicking on coin items, sometimes the wrong coin is displayed when expanded.
 * When the bottom sheet is collapsed and I click on it, the coin behind it is selected
 * In fragmentCoinsList.xml, don't use px, use dp instead.
 *      Then set bottomSheetBehavior.peekHeight to the same dp (from px)
 */

/** maybe:
 * - calculate percent change from user click in chart, to current price (I love this feature)
 * - local database
 *      - maybe for favorites
 * - Consider including OHLC
 *      - https://api.coinpaprika.com/#tag/Coins/paths/~1coins~1{coin_id}~1ohlcv~1latest~1/get
 * - list of exchanges
 * - price converter
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