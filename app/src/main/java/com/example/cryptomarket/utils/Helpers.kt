package com.example.cryptomarket.utils

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