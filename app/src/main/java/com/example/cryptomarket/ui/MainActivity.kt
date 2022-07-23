package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

// ATH = all time high
/** todo:
 * - market overview
 *      - list the top 10 coins at the top right of the scroll view
 *          - (where the empty space is)
 * - put API data in charts
 *      - coins list item
 *      - sheet expanded
 *      - sheet collapsed
 * - colors dark mode
 * - app icon
 * - colors light mode
 * - news article link navigate to the website. When click on news recycler item
 * - customize the charts
 * - maybe put more logic in the viewModel
 * - bugs (at least the first 1, maybe just leave the rest for "eventually")
 */

/** bugs:
 * - Make more data class attributes optionals. Sometime API data is null
 *
 * - Start fragment is created twice at startup. Maybe other views are too
 * - Some chart data are not loaded within a good amount of time, idk what triggers eventual loading (some seem to never load)
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