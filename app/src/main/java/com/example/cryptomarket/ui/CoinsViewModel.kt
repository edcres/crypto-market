package com.example.cryptomarket.ui

import androidx.lifecycle.ViewModel
import com.example.cryptomarket.data.Repository

private const val TAG = "CoinsVM__TAG"

class CoinsViewModel: ViewModel() {
    private lateinit var repo: Repository

    // SETUP //
    fun startApplication() {
        repo = Repository()
//        collectAllWorkouts()
    }
    // SETUP //
}