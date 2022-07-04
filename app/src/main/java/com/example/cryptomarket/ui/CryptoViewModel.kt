package com.example.cryptomarket.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptomarket.data.Repository

private const val TAG = "CoinsVM__TAG"

class CryptoViewModel: ViewModel() {

    private lateinit var repo: Repository
    private var _fragChosen = MutableLiveData<Any>()
    val fragChosen: LiveData<Any> get() = _fragChosen

    // SETUP //
    fun startApplication() {
        repo = Repository()
    }
    // SETUP //
}