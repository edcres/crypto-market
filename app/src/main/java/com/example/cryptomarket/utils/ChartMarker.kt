package com.example.cryptomarket.utils

import android.content.Context
import android.widget.TextView
import com.example.cryptomarket.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils

// todo: Edit this marker
class ChartMarker(context: Context?, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.marker_content)

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        if (e is CandleEntry) {
//            tvContent.text = Utils.formatNumber(e.high, 0, true)
            val textString = presentPriceFormatUSD("", e.y.toDouble())
            tvContent.text = textString
        } else {
//            tvContent.text = Utils.formatNumber(e.y, 0, true)
            tvContent.text = presentPriceFormatUSD("", e.y.toDouble())
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}