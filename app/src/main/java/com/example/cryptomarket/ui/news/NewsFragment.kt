package com.example.cryptomarket.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.FragmentCoinsListBinding
import com.example.cryptomarket.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNewsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
}