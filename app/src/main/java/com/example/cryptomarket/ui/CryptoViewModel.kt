package com.example.cryptomarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel: ViewModel() {

    private lateinit var repo: Repository
    private var _fragChosen = MutableLiveData<FragChosen>()
    val fragChosen: LiveData<FragChosen> get() = _fragChosen

    // SETUP //
    fun startApplication() {
        repo = Repository()
    }
    // SETUP //

    // HELPERS //
    fun setFragChosen(chosenFrag: FragChosen) {
        _fragChosen.postValue(chosenFrag)
    }
    // HELPERS //

    // REPO QUERIES //
    fun getHistoricalTickerData(timeFrame: DateFrame): List<HistoricalTicker> {
        // todo: make a db query to get the chart data.
        //  /historical?start=2022-07-01&interval=1d
        // if the data frame is 1w, the interval is 1d
        // get the start dat using 'timeFrame: DateFrame' and send the request to the repo
    }
    // REPO QUERIES //
}