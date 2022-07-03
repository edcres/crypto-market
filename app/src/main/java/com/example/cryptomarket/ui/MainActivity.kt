package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * *
 * - make bottom navigation
 *      - coins
 *          - sheet fragment
 *          - .xml
 *      - news
 *          - sheet fragment
 *          - .xml
 *      - curved edges
 *      - use standard sheet
 *      - figure out how to get it up through the fragment
 *          - activity
 *          - start fragment (click listener communicates with the activity, probably)
 * - animations for transitions between fragments (left: from right, middle: zoom in, zoom out, right: from left)
 *
 * - data/backend stuff
 *      - Data class for the API
 * - display the data in the view as text
 *      - recyclerView for coins list
 *      - recyclerView for news list
 *      - overview data
 * - appbar
 *
 * AFTER:
 * - put data in charts
 * - customize the charts
 */

/** maybe:
 * - news section
 * - local database
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