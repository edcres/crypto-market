package com.example.cryptomarket.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.coin.CoinData
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.data.newsapi.NewsCall
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen
import com.example.cryptomarket.utils.addZerosToDate
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel : ViewModel() {

    private lateinit var repo: Repository
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
    val chartsData = mutableMapOf<String, List<HistoricalTicker>>()

    init { startApplication() }

    // SETUP //
    private fun startApplication() {
        Log.d(TAG, "startApplication called")
        repo = Repository()
        viewModelScope.launch { _tickers.postValue(repo.getTickers()) }
        viewModelScope.launch { _globalData.postValue(repo.getGlobalData()) }
        viewModelScope.launch { _newsCall.postValue(repo.getNewsPosts()) }
    }
    // SETUP //

    // HELPERS //
    fun setTickerClicked(ticker: Ticker) { _tickerClicked.postValue(ticker) }
    fun setFragChosen(chosenFrag: FragChosen) { _fragChosen.postValue(chosenFrag) }
    // HELPERS //

    // REPO QUERIES //
    fun getHistoricalTickerData(forSheet: Boolean, tickerId: String, timeFrame: DateFrame):
            LiveData<List<HistoricalTicker>> {
        Log.d(TAG, "getHistoricalTickerData: \n$tickerId\n$timeFrame")
        val tickerData = MutableLiveData<List<HistoricalTicker>>()
        viewModelScope.launch {
            // Check if chartsData contains tickerID,
            //  if not then do an API query and add the data to chartsData.
            if (chartsData.contains(tickerId) && !forSheet) {
                Log.d(TAG, "contains id: true")
                // Only works for week
                //  (To use a different timeframe, use a different map or delete the week data)
                tickerData.postValue(chartsData[tickerId])
            } else {
                Log.d(TAG, "contains id: false")
                // Todo: tests these in API queries
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
                val startTime = "${currentDate.get(Calendar.YEAR)}/" +
                        "${currentDate.get(Calendar.MONTH) + 1}/" +
                        "${currentDate.get(Calendar.DAY_OF_MONTH) + 1}"
                // Handle when the API rejects the request when making too many.
                try {
                    val historicalData = repo
                        .getHistoricalTickers(tickerId, addZerosToDate(startTime), timeFrame.interval)
                    tickerData.postValue(historicalData)
                    chartsData[tickerId] = historicalData
                } catch (e: HttpException) {
                    Log.e(TAG, "getHistoricalTickerData: \n$e")
                }
            }
        }
        return tickerData
    }
    fun getCoinData(coinID: String): LiveData<CoinData> {
        val coinData = MutableLiveData<CoinData>()
        viewModelScope.launch { coinData.postValue(repo.getCoinData(coinID)) }
        return coinData
    }
    // REPO QUERIES //
}