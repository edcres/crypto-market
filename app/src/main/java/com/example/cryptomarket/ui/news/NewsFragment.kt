package com.example.cryptomarket.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptomarket.R
import com.example.cryptomarket.databinding.FragmentNewsBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.FragChosen
import java.net.URL

private const val TAG = "NewsListFrag__TAG"

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private val vm: CryptoViewModel by activityViewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentNewsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListAdapter = NewsListAdapter(vm)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            newsListRecycler.adapter = newsListAdapter
            newsListRecycler.layoutManager = LinearLayoutManager(requireContext())
            newsWebView.settings.javaScriptEnabled = true
            newsWebView.setBackgroundColor(resources.getColor(R.color.bottom_nav))
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                newsWebView.goBack()
            }
            closeWebBtn.setOnClickListener { closeWebView() }
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
            when (it) {
                FragChosen.MARKET -> navController.navigate(R.id.action_news_to_market)
                FragChosen.COINS -> navController.navigate(R.id.action_news_to_coins)
                else -> Log.i(TAG, "setObservers: from News to $it")
            }
        }
        vm.newsCall.observe(viewLifecycleOwner) { newsListAdapter.submitList(it.results) }
        vm.newsClicked.observe(viewLifecycleOwner) { showWebView(it) }
    }
    // SETUP //

    // WEB VIEW CLIENT //
    private class CustomFormClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(webview: WebView, url: String): Boolean {
            webview.loadUrl(url)
            Log.d(TAG, "shouldOverrideUrlLoading:\n$url")
            return true
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            if (request != null) Log.d(TAG, "onReceivedError:\n${request.url}")
            super.onReceivedError(view, request, error)
        }
    }
    // WEB VIEW CLIENT //

    // HELPERS //
    private fun showWebView(link: String) {
        binding?.apply {
            val url = URL(link)
            url.openConnection()
            newsWebView.webViewClient = CustomFormClient()
            newsWebView.loadUrl(link)
            newsWebView.visibility = View.VISIBLE
            webAppBar.visibility = View.VISIBLE
        }
    }

    private fun closeWebView() {
        binding?.apply {
            newsWebView.visibility = View.GONE
            webAppBar.visibility = View.GONE
            // Set it to a safe website
//            newsWebView.loadUrl("https://" + "cryptopanic.com");
        }
    }
    // HELPERS //
}