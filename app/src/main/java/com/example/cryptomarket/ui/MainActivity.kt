package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - bugs (at least the first 2, maybe just leave the rest for "eventually")
 * - todos, warnings, etc
 * - customize the expanded chart
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
 * - Make more data class attributes optionals. Sometime API data is null
 * - collapsed sheet '1w: -3.24%' does not update with timeframe change
 * - MAJOR: Wrong data is displayed on the expanded sheet sometimes.
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