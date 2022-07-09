package com.example.cryptomarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen
import java.util.*

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
    fun getHistoricalTickerData(
        startDate: String,
        interval: Int,
        timeFrame: DateFrame
    ): List<HistoricalTicker> {
        // todo:

    }
    // REPO QUERIES //
}