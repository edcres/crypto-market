package com.example.cryptomarket.utils

import com.example.cryptomarket.data.coinsapi.ticker.PriceData

enum class FragChosen {
    MARKET, COINS, NEWS
}
// 'abbre' means abbreviation
// todo: play around with the intervals (test how it looks with the data)
//      (probably get more data for the table)
enum class DateFrame(val abbrev: Char, val interval: String) {
    DAY('d', "1h"),
    WEEK('w', "1d"),
    MONTH('m', "1d"),
    QUARTER('q', "1w"),
    YEAR('y', "1w")
}

fun pickPercentChange(timeFrame: DateFrame, priceData: PriceData) = when(timeFrame) {
        DateFrame.DAY -> priceData.percent_change_24h
        DateFrame.WEEK -> priceData.percent_change_7d
        DateFrame.MONTH -> priceData.percent_change_30d
        DateFrame.QUARTER -> null
        DateFrame.YEAR -> priceData.percent_change_1y
}

fun formatNewsPostDate(rawDateString: String): String {
    // raw date = 2022-07-08T23:27:51Z
    // raw date -> y/m/d -> m/d/y
    // raw date -> 2022/07/08 -> 07/08/2022
    val listYMD = rawDateString.split("-").map {
        if (it.contains(":")) it.subSequence(0,2) else it
    }
    return "${listYMD[1]}/${listYMD[2]}/${listYMD[0]}"
}

fun addZerosToDate(baseDate: String) =
    baseDate.split("/").joinToString("/") {
        if (it.length == 1) {
            "0$it"
        } else it
    }
