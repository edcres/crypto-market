package com.example.cryptomarket.utils

import com.example.cryptomarket.data.coinsapi.ticker.PriceData

enum class FragChosen {
    MARKET, COINS, NEWS
}
// 'abbre' means abbreviation
// todo: play around with the intervals (test how it looks with the data) (probably get more data for the table)
enum class DateFrame(val abbre: Char, val interval: String, val percentChange: Double) {
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