package com.example.cryptomarket.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.newsapi.NewsPost
import com.example.cryptomarket.databinding.NewsRecyclerItemBinding

class NewsListAdapter :
    ListAdapter<NewsPost, NewsListAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class NewsViewHolder private constructor(
        private val binding: NewsRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(newsPost: NewsPost) {
            binding.apply {
                newsTitleTxt.text = newsPost.title
                sourceDomain.text = newsPost.domain
                // todo: turn this into a better date format
                datePublishedTxt.text = newsPost.publishedAt
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return NewsViewHolder(binding)
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