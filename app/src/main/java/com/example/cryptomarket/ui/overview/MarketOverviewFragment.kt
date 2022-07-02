package com.example.cryptomarket.ui.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.FragmentMarketOverviewBinding
import com.example.cryptomarket.databinding.FragmentNewsBinding

class MarketOverviewFragment : Fragment() {

    private var binding: FragmentMarketOverviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentMarketOverviewBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
}