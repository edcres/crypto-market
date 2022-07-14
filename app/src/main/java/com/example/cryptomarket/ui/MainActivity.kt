package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

// ATH = all time high
/** todo:
 * - UI
 *      - display numbers in an appropriate way (45.0 -> 45) (0.3242432423 -> 0.13)
 *          - news
 *              - hide the chart data
 *              - format the rest of the numbers
 *          - coins
 *              - coin item
 *              - sheet collapsed
 *              - sheet expanded
 *      - display texts in a better way
 *          - with their label
 *          - coins
 *          - news
 *
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
 * - display moreInfo better (maybe say the info is unavailable) (also, empty list still shows [])
 *
 * AFTER:
 * - put API data in charts
 *      - customize the charts
 * - decide what more I want to display in market overview
 * - colors dark mode
 * - app icon
 * - colors light mode
 * - news article link navigate to the website. When click on news recycler item
 * - maybe put more logic in the viewModel
 * bugs (maybe just leave them for "eventually")
 */

/** bugs:
 * - Make more data class attributes optionals. Sometime API data is null
 * - When the bottom sheet is expanded and I click somewhere where there's a recycler item behind,
 *      the coin behind it is displayed on the bottom sheet.
 *          - doesn't happen if im clicking on a cardView or a widget with a click listener
 *          - maybe add a click listener on the background (the container view)
 *                  and it doesn't do anything
 *
 * - start fragment is created twice at startup. Maybe other views are too
 * - Some charts are not loaded within a good amount of time, idk what triggers eventually
 *      - put the queries that make it crash in a queue to be don later and store the
 *                  in the vm map (I don't need this put it's cool)
 *          - maybe override onViewRecycled(on the adapter)
 *          - maybe a map <id, request>
 *          - maybe there's something in retrofit or another library that queues requests
 *      - quick fix (kind of): click on the item,
 *              when its loaded on the sheet load it to the recycler item
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