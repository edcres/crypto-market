package com.example.cryptomarket.utils

import com.example.cryptomarket.data.coinsapi.ticker.PriceData

enum class FragChosen {
    MARKET, COINS, NEWS
}
// 'abbre' means abbreviation
// todo: play around with the intervals (test how it looks with the data) (probably get more data for the table)
enum class DateFrame(val abbre: Char, val interval: String) {
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

// 2022-07-08T23:27:51Z
fun formatNewsPostDate(rawDateString: String): String {
    val dateList = rawDateString.split("-")
    val dayOfMonth = "${dateList[2][0]}${dateList[2][1]}"
    return "${dateList[0]}/${dateList[1]}/$dayOfMonth"
}