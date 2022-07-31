package com.example.cryptomarket.ui

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.PriceData
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.data.newsapi.NewsCall
import com.example.cryptomarket.utils.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.*

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel : ViewModel() {

    private var repo: Repository = Repository()
    private var _fragChosen = MutableLiveData<FragChosen>()
    val fragChosen: LiveData<FragChosen> get() = _fragChosen
    private var _tickerClicked = MutableLiveData<Ticker>()
    val tickerClicked: LiveData<Ticker> get() = _tickerClicked

    private var _tickers = MutableLiveData<List<Ticker>>()
    val tickers: LiveData<List<Ticker>> get() = _tickers
    private var _globalData = MutableLiveData<GlobalData>()
    val globalData: LiveData<GlobalData> get() = _globalData
    private var _newsCall = MutableLiveData<NewsCall>()
    val newsCall: LiveData<NewsCall> get() = _newsCall

    // Used to store the data of chart queries, to avoid extra API queries.
    private val chartsData = mutableMapOf<String, List<HistoricalTicker>>()

    init {
            viewModelScope.launch {
                try { _tickers.postValue(repo.getTickers()) }
                catch (e: UnknownHostException) {
                    Log.e(TAG, "Error getting tickers data: $e")
                }
            }
            viewModelScope.launch {
                try { _globalData.postValue(repo.getGlobalData()) }
                catch (e: UnknownHostException) {
                    Log.e(TAG, "Error getting global data: $e")
                }
            }
            viewModelScope.launch {
                try { _newsCall.postValue(repo.getNewsPosts()) }
                catch (e: UnknownHostException) {
                    Log.e(TAG, "Error getting news data: $e")
                }
            }
    }

    // HELPERS //
    fun setTickerClicked(ticker: Ticker) { _tickerClicked.postValue(ticker) }
    fun setFragChosen(chosenFrag: FragChosen) { _fragChosen.postValue(chosenFrag) }
    // HELPERS //

    // REPO QUERIES //
    // todo: clean upo this code. Consider that the month might not have > 28 days and
    //      'Calendar.DAY_OF_MONTH, -7' does not cover this bug
    fun getHistoricalTickerData(forSheet: Boolean, tickerId: String, timeFrame: DateFrame):
            LiveData<List<HistoricalTicker>> {
        val tickerData = MutableLiveData<List<HistoricalTicker>>()
        viewModelScope.launch {
            // Check if chartsData contains tickerID,
            //  if not then do an API query and add the data to chartsData.
            if (chartsData.contains(tickerId) && !forSheet) {
                // Only works for week
                //  (To use a different timeframe, use a different map or delete the week data)
                tickerData.postValue(chartsData[tickerId])
            } else {
                // get current date yyyy/mm/dd
                val currentDate = Calendar.getInstance()
                when (timeFrame) {
//                    DateFrame.DAY -> currentDate.add(Calendar.DAY_OF_MONTH, -1)
                    DateFrame.WEEK -> currentDate.add(Calendar.DAY_OF_MONTH, -7)
                    DateFrame.MONTH -> currentDate.add(Calendar.MONTH, -1)
                    DateFrame.QUARTER -> currentDate.add(Calendar.MONTH, -3)
                    DateFrame.HALF_YEAR -> currentDate.add(Calendar.MONTH, -6)
                    DateFrame.YEAR -> {
                        currentDate.add(Calendar.YEAR, -1)
                        currentDate.add(Calendar.DAY_OF_YEAR, +1)
                    }
                }
                var startTime = "${currentDate.get(Calendar.YEAR)}/" +
                        "${currentDate.get(Calendar.MONTH) + 1}/" +
                        "${currentDate.get(Calendar.DAY_OF_MONTH) + 1}"
                startTime = getCorrectDayOfMonth(startTime)
                // Handle when the API rejects the request when making too many.
                try {
                    try {
                        Log.d(TAG, "getHistoricalTickerData: " +
                            "\nid = $tickerId" +
                            "\nstart = ${addZerosToDate(startTime)}" +
                            "\ninterval ${timeFrame.interval}")
                        val historicalData = repo
                            .getHistoricalTickers(
                                tickerId,
                                addZerosToDate(startTime),
                                timeFrame.interval
                            )
                        tickerData.postValue(historicalData)
                        chartsData[tickerId] = historicalData
                    } catch (e: HttpException) {
                        Log.e(TAG, "getHistoricalTickerData: \n$e")
//                        try {
//                            // Cover up a bug if the month doesn't have > 28 days (make day the 28th).
//                            Log.d(TAG, "getHistoricalTickerData: $startTime")
//                            val dayOfMonth = startTime.split("/")[2]
//                            // If the crash happened and the day of month is over 28.
//                            if (dayOfMonth.toInt() > 28) {
//                                Log.d(TAG, "getHistoricalTickerData: > 28")
//                                val newStartSplit = startTime.split("/")
//                                val newStartTime = "${newStartSplit[0]}/${newStartSplit[1]}/28"
//                                Log.d(TAG, "getHistoricalTickerData: new start $newStartTime")
//                                val historicalData = repo
//                                    .getHistoricalTickers(
//                                        tickerId,
//                                        addZerosToDate(newStartTime),
//                                        timeFrame.interval
//                                    )
//                                tickerData.postValue(historicalData)
//                                chartsData[tickerId] = historicalData
//                            } else Log.d(TAG, "getHistoricalTickerData: NOT > 28")
//                        } catch (e: HttpException) {
//                            Log.e(TAG, "probably too many requests $e")
//                        }
                    }
                } catch (e: UnknownHostException) {
                    Log.e(TAG, "Error Getting Historical Ticker Data $e")
                }
            }
        }
        return tickerData
    }
    fun getCoinData(coinID: String): LiveData<CoinData> {
        val coinData = MutableLiveData<CoinData>()
        viewModelScope.launch {
            try {
                coinData.postValue(repo.getCoinData(coinID))
            } catch (e: UnknownHostException) {
                Log.e(TAG, "Error getting coin data $e")
            }
        }
        return coinData
    }
    // REPO QUERIES //
}