package com.example.cryptomarket.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.FragmentNewsBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.FragChosen

private const val TAG = "NewsListFrag__TAG"

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private val vm: CryptoViewModel by activityViewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentNewsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListAdapter = NewsListAdapter()
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            newsListRecycler.adapter = newsListAdapter
            newsListRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        setObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // SETUP //
    private fun setObservers() {
        vm.fragChosen.observe(viewLifecycleOwner) {
            val navController = findNavController()
            when(it) {
                FragChosen.MARKET -> navController.navigate(R.id.action_news_to_market)
                FragChosen.COINS -> navController.navigate(R.id.action_news_to_coins)
                else -> Log.i(TAG, "setObservers: from News to $it")
            }
        }
        vm.newsCall.observe(viewLifecycleOwner) { newsListAdapter.submitList(it.results) }
    }
    // SETUP //
}