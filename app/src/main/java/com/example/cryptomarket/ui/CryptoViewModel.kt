package com.example.cryptomarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptomarket.data.Repository
import com.example.cryptomarket.data.coinsapi.ticker.HistoricalTicker
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.FragChosen
import com.example.cryptomarket.utils.addZerosToDate
import java.util.*

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel : ViewModel() {

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
    fun getHistoricalTickerData(timeFrame: DateFrame = DateFrame.WEEK):
            LiveData<List<HistoricalTicker>> {
        // todo: do this in a background thread
        // Todo: tests these in API queries
        // get current date yyyy/mm/dd
        val currentDate = Calendar.getInstance()
        when (timeFrame) {
            DateFrame.DAY -> currentDate.add(Calendar.DAY_OF_MONTH, -1)
            DateFrame.WEEK -> currentDate.add(Calendar.DAY_OF_MONTH, -7)
            DateFrame.MONTH -> currentDate.add(Calendar.MONTH, -1)
            DateFrame.QUARTER -> currentDate.add(Calendar.MONTH, -3)    // Starts three months past
            DateFrame.YEAR -> currentDate.add(Calendar.YEAR, -1)
        }
        val startTime =
            "${currentDate.get(Calendar.YEAR)}/${currentDate.get(Calendar.MONTH) + 1}/" +
                    "${currentDate.get(Calendar.DAY_OF_MONTH)}"

        repo.getHistoricalTickers(addZerosToDate(startTime), timeFrame.interval)
        return
    }
    // REPO QUERIES //
}