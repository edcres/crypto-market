package com.example.cryptomarket.ui.coins

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.DummyDataClass

class CoinsListAdapter()
    : ListAdapter<DummyDataClass, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinsListAdapter.CoinsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CoinsListAdapter.CoinsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class CoinsViewHolder private constructor(
        val binding: 
    ): RecyclerView.ViewHolder {

    }

    class CoinsDiffCallback : DiffUtil.ItemCallback<DummyDataClass>() {
        override fun areItemsTheSame(oldItem: DummyDataClass, newItem: DummyDataClass): Boolean {
            return oldItem.data == newItem.data
        }
        override fun areContentsTheSame(oldItem: DummyDataClass, newItem: DummyDataClass): Boolean {
            return oldItem == newItem
        }
    }
}