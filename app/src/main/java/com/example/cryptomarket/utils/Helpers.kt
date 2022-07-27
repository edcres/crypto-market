package com.example.cryptomarket.utils

import android.util.Log
import com.example.cryptomarket.data.coinsapi.TeamMember
import com.example.cryptomarket.data.coinsapi.ticker.PriceData
import java.text.DecimalFormat
import java.util.*

const val GLOBAL_TAG = "Global__TAG"

enum class FragChosen { MARKET, COINS, NEWS }

// 'abbre' means abbreviation
enum class DateFrame(val abbrev: String, val interval: String) {
    //    DAY('d', "1h"),
    WEEK("1w", "1d"),
    MONTH("1m", "1d"),
    QUARTER("1q", "1d"),
    HALF_YEAR("6m", "1d"),
    YEAR("1y", "7d")
}

fun pickPercentChange(timeFrame: DateFrame, priceData: PriceData) = when (timeFrame) {
//    DateFrame.DAY -> priceData.percentChange24h
    DateFrame.WEEK -> "${priceData.percentChange7d}%"
    DateFrame.MONTH -> "${priceData.percentChange30d}%"
    DateFrame.QUARTER -> null
    DateFrame.HALF_YEAR -> null
    DateFrame.YEAR -> "${priceData.percentChange1y}%"
}

fun reformatDate(rawDateString: String?): String {
    // raw date = 2022-07-08T23:27:51Z
    // raw date -> y/m/d -> m/d/y
    // raw date -> 2022/07/08 -> 07/08/2022
    if (rawDateString == null) return "date not available"
    val listYMD = rawDateString.split("-").map {
        if (it.contains(":")) it.subSequence(0, 2) else it
    }
    return "${listYMD[1]}/${listYMD[2]}/${listYMD[0]}"
}

fun addZerosToDate(baseDate: String) =
    baseDate.split("/").joinToString("-") {
        if (it.length == 1) {
            "0$it"
        } else it
    }

fun getPercentChangeNum(percentChangeString: String) : Double =
    percentChangeString.split("%")[0].toDouble()

fun displayTeam(team: List<TeamMember>?): String {
    val teamList = mutableListOf<String>()
    val altLabel = "Team not available"
    if (team != null) {
        if (team.isNotEmpty()) {
            for (i in team) {
                teamList.add("${i.position}, ${i.name}")
            }
        } else return altLabel
    } else return altLabel
    return teamList.joinToString("\n")
}

fun displayIsOpenSource(isOpenSource: Boolean?) = when (isOpenSource) {
    true -> "Open Source"
    false -> "Not open source."
    else -> ""
}

fun displayMoreInfo(label: String, info: String?) = if (info != null) {
        "$label $info"
    } else "$label not available"

fun displayStartedAt(startDate: String?) = if (startDate != null) "Started at $startDate" else ""

fun displayATHPrice(athPrice: Double?) = if (athPrice != null) "ATH $${athPrice}" else ""

fun displayATHDate(athDate: String?) = if (athDate != null) "ATH $${athDate}" else ""

fun removeTrailing2Zeros(num: String): String {
    return if (num.contains('.')) {
        val decimals = num.split('.').last()
        if (decimals.length <= 2 && decimals.first() == '0' && decimals.last() == '0') {
            num.split('.')[0]
        } else num
    } else num
}

fun roundToNDecimals(num: Double, n: Int) = "%.${n}f".format(num)

// Note: it doesn't round up the decimals, it just cuts the rest off.
fun presentPriceFormatUSD(label: String, num: Double?) =
    if (num != null){
        if (num < 10) "$label$${"%,.6f".format(Locale.ENGLISH, num)}"
        else "$label$${"%,.2f".format(Locale.ENGLISH, num)}"
    } else "$label price not available"

fun displayLong(num: Long): String {
    val dec = DecimalFormat("#,###,###")
    return dec.format(num)
}

// todo: maybe get rid of this
fun displayPercent(label: String, num: Double) = "$label${removeTrailing2Zeros(num.toString())}%"

fun displayHashAlgorithm(hash: String?) = if (hash != null) {
        "Hash algorithm: $hash"
    } else "Hash algorithm not available"