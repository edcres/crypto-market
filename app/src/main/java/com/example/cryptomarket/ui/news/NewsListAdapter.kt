package com.example.cryptomarket.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.newsapi.NewsPost
import com.example.cryptomarket.databinding.NewsRecyclerItemBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.reformatDate

class NewsListAdapter(private val vm: CryptoViewModel) :
    ListAdapter<NewsPost, NewsListAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder.from(vm, parent)

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class NewsViewHolder private constructor(
        private val vm: CryptoViewModel,
        private val binding: NewsRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsPost: NewsPost) {
            binding.apply {
                newsTitleTxt.text = newsPost.title
                sourceDomain.text = newsPost.domain
                datePublishedTxt.text = reformatDate(newsPost.publishedAt)
                newsItemContainer.setOnClickListener {
                    vm.setNewsClicked(newsPost.url)
                }
                sourceDomain.setOnClickListener { vm.setNewsClicked("https://${newsPost.domain}") }
                executePendingBindings()
            }
        }

        companion object {
            fun from(
                vm: CryptoViewModel,
                parent: ViewGroup
            ): NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return NewsViewHolder(vm, binding)
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<NewsPost>() {
        override fun areItemsTheSame(oldItem: NewsPost, newItem: NewsPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsPost, newItem: NewsPost): Boolean {
            return oldItem == newItem
        }
    }
}