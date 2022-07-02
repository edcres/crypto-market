package com.example.cryptomarket.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.DummyDataClass
import com.example.cryptomarket.databinding.DummyRecyclerItemBinding

class NewsListAdapter() :
    ListAdapter<DummyDataClass, NewsListAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class NewsViewHolder private constructor(
        private val binding: DummyRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(dummyDataClass: DummyDataClass) {
            binding.apply {
                // todo:
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DummyRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return NewsViewHolder(binding)
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<DummyDataClass>() {
        override fun areItemsTheSame(oldItem: DummyDataClass, newItem: DummyDataClass): Boolean {
            return oldItem.data == newItem.data
        }
        override fun areContentsTheSame(oldItem: DummyDataClass, newItem: DummyDataClass): Boolean {
            return oldItem == newItem
        }
    }
}