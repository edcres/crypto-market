package com.example.cryptomarket.utils

import android.util.Log
import com.example.cryptomarket.data.coinsapi.TeamMember
import com.example.cryptomarket.data.coinsapi.ticker.PriceData
import java.util.*

const val GLOBAL_TAG = "Global__TAG"

enum class FragChosen {
    MARKET, COINS, NEWS
}

// 'abbre' means abbreviation
// todo: play around with the intervals (test how it looks with the data)
//      (probably get more data for the table)
enum class DateFrame(val abbrev: String, val interval: String) {
//    DAY('d', "1h"),
    WEEK("1w", "1d"),
    MONTH("1m", "1d"),
    QUARTER("1q", "7d"),
    HALF_YEAR("6m", "7d"),
    YEAR("1y", "7d")
}

fun pickPercentChange(timeFrame: DateFrame, priceData: PriceData) = when (timeFrame) {
//    DateFrame.DAY -> priceData.percentChange24h
    DateFrame.WEEK -> priceData.percentChange7d
    DateFrame.MONTH -> priceData.percentChange30d
    DateFrame.QUARTER -> null
    DateFrame.HALF_YEAR -> null
    DateFrame.YEAR -> priceData.percentChange1y
}

fun reformatDate(rawDateString: String): String {
    // raw date = 2022-07-08T23:27:51Z
    // raw date -> y/m/d -> m/d/y
    // raw date -> 2022/07/08 -> 07/08/2022
    val listYMD = rawDateString.split("-").map {
        Log.d(GLOBAL_TAG, "reformatDate: $it")
        if (it.contains(":")) it.subSequence(0, 2) else it
    }
    Log.d(GLOBAL_TAG, "reformatDate: $listYMD")
    return "${listYMD[1]}/${listYMD[2]}/${listYMD[0]}"
}

fun addZerosToDate(baseDate: String) =
    baseDate.split("/").joinToString("-") {
        if (it.length == 1) {
            "0$it"
        } else it
    }

fun displayTeam(team: List<TeamMember>?): String =
    // todo: make it presentable
    if (team != null) {
        if (team.isEmpty()) team.toString() else ""
    } else ""

fun displayIsOpenSource(isOpenSource: Boolean?) = when (isOpenSource) {
    true -> "Open Source"
    false -> "Not open source."
    else -> ""
}

fun displayStartedAt(startDate: String?) = if(startDate != null) "Started at $startDate" else ""

fun displayATHPrice(athPrice: Double?) = if (athPrice != null) { "ATH $${athPrice}" } else ""

fun displayATHDate(athDate: String?) = if (athDate != null) { "ATH $${athDate}" } else ""

fun removeTrailingZeros(num: Double): String {
    return if (num.toString().contains(".")){
        val decimals = num.toString().split(".").last()
        if (decimals.length == 1 && decimals.first() == '0') num.toInt().toString()
        else num.toString()
    } else num.toString()
}

fun roundToNDecimals(num:Double, n: Int) = "%.${n}f".format(num)

// Note: it doesn't round up the decimals, it just cuts the rest off.
fun presentPriceFormatUSD(label: String, num: Double) =
    "$label: $${"%,.2f".format(Locale.ENGLISH, num)}"

fun displayPercent(label: String, num: Double) = "$label: ${removeTrailingZeros(num)}%"