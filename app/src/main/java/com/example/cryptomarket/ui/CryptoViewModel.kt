package com.example.cryptomarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.GlobalData
import com.example.cryptomarket.data.coinsapi.coin.CoinFromList
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.data.newsapi.NewsCall
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen
import com.example.cryptomarket.utils.addZerosToDate
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel : ViewModel() {

    private lateinit var repo: Repository
    private var _fragChosen = MutableLiveData<FragChosen>()
    val fragChosen: LiveData<FragChosen> get() = _fragChosen

    private var _coinFromList = MutableLiveData<List<CoinFromList>>()
    val coinFromList: LiveData<List<CoinFromList>> get() = _coinFromList
    private var _globalData = MutableLiveData<List<GlobalData>>()
    val globalData: LiveData<List<GlobalData>> get() = _globalData
    private var _newsCall = MutableLiveData<List<NewsCall>>()
    val newsCall: LiveData<List<NewsCall>> get() = _newsCall

    // SETUP //
    fun startApplication() {
        repo = Repository()
        viewModelScope.launch {
            // todo:
        }
    }
    // SETUP //

    // HELPERS //
    fun setFragChosen(chosenFrag: FragChosen) {
        _fragChosen.postValue(chosenFrag)
    }
    // HELPERS //

    // REPO QUERIES //
    fun getHistoricalTickerData(timeFrame: DateFrame = DateFrame.WEEK):
            LiveData<List<HistoricalTicker>> {
        val tickerData = MutableLiveData<List<HistoricalTicker>>()
        viewModelScope.launch {
            // Todo: tests these in API queries
            // get current date yyyy/mm/dd
            val currentDate = Calendar.getInstance()
            when (timeFrame) {
                DateFrame.DAY -> currentDate.add(Calendar.DAY_OF_MONTH, -1)
                DateFrame.WEEK -> currentDate.add(Calendar.DAY_OF_MONTH, -7)
                DateFrame.MONTH -> currentDate.add(Calendar.MONTH, -1)
                DateFrame.QUARTER -> currentDate.add(Calendar.MONTH, -3) // Starts three months past
                DateFrame.YEAR -> currentDate.add(Calendar.YEAR, -1)
            }
            val startTime =
                "${currentDate.get(Calendar.YEAR)}/${currentDate.get(Calendar.MONTH) + 1}/" +
                        "${currentDate.get(Calendar.DAY_OF_MONTH)}"
            tickerData
                .postValue(repo.getHistoricalTickers(addZerosToDate(startTime), timeFrame.interval))
        }
        return tickerData
    }
    // REPO QUERIES //
}