package com.example.cryptomarket.utils

enum class FragChosen {
    MARKET, COINS, NEWS
}
enum class DateFrame(val abbreviation: Char) {
    DAY('d'),
    WEEK('w'),
    MONTH('m'),
    YEAR('y')
}