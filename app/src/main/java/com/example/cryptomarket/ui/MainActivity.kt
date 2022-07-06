package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - configure bottom sheets for the API data
 *      - coins list
 *      - news
 *
 * - data/backend stuff
 *      - Data class for the API
 * - display the data in the view as text
 *      - recyclerView for coins list
 *      - recyclerView for news list
 *      - overview data
 *
 * AFTER:
 * - put data in charts
 *      - customize the charts
 * - colors dark mode
 * - app icon
 * - colors light mode
 */

/** maybe:
 * - local database
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