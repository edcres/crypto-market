package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** todo:
 * - customize the expanded chart
 *          - fading background below the line
 *          - remove y-axis (horizontal) marker line
 *          - Marker (in the ChartMarker file in utils)
 *              - marker lines color
 *              - marker text color
 *          - put the x-axis numbers at the bottom
 *          - maybe put a circle at the center of the marker lines
 * - todos, warnings, imports
 * - maybe put more logic in the viewModel
 * - colors dark mode
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