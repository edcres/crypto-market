package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - market overview layout (maybe)
 *      - smaller text size
 *      - reorganize the layout (new branch)
 *      - merge branches
 * - News fragment:
 *      - news article link navigate to the website. When click on news recycler item
 *
 * - bugs (at least the first 2, maybe just leave the rest for "eventually")
 * - customize the charts
 *      - expanded chart
 *          - fading background below the line
 *          - remove y-axis (horizontal) marker line
 *          - marker lines color
 *          - marker text color
 *          - put the x-axis numbers at the bottom
 *          - maybe put a circle at the center of the marker lines
 * - maybe put more logic in the viewModel
 * - colors dark mode
 */

/** bugs:
 * - collapsed sheet '1w: -3.24%' does not update with timeframe change
 * - MAJOR: Wrong data is displayed on the expanded sheet sometimes.
 * - Make more data class attributes optionals. Sometime API data is null
 *
 * - Start fragment is created twice at startup. Maybe other views are too
 * - (Looks like this is fixed now, maybe it was a problem
 *      displaying too big strings in the textViews) Some chart data are not loaded
 *      within a good amount of time, idk what triggers eventual loading (some seem to never load)
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
 * Remove unused imports
 * format code
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