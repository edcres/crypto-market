package com.example.cryptomarket.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.cryptomarket.R

/** App explanation:
 *
 * The app has 3 main navigation destinations
 * - Coins List
 *      - Displays a list of crypto coins with a simple graph
 *      - A bottom expandable sheet with a specific coin
 *      - The user clicks on a coin and the bottom sheet expands
 *          - Expanded, the sheet displays more information about the coin
 *              - Including a detailed interactive line chart
 * - Market Overview
 *      - Displays broad information about the overall cryptocurrency market
 *          - Along with Pie charts comparing some of the top 10 coins
 * - News List
 *      - Displays a list of news about cryptocurrency
 *          - each is clickable and displays a Review and the content of
 *              a link to the news article.
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