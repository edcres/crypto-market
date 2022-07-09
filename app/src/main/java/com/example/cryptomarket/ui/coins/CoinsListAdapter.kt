package com.example.cryptomarket.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.CoinChartRecyclerItemBinding

class CoinsListAdapter() :
    ListAdapter<Ticker, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CoinsViewHolder private constructor(
        private val binding: CoinChartRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(ticker: Ticker) {
            binding.apply {
                coinSymbolTxt.text = ticker.symbol
                coinNameTxt.text = ticker.name
                rankTxt.text = ticker.rank.toString()
                if (ticker.quotes.isNotEmpty()) {
                    // todo: possible bug: although in practice it's probably no problem
                    //      the price might not be in USD
                    priceTxt.text = ticker.quotes[0].price.toString()
                    // todo: percent change should change base on the
                    //      parameters of the data in the chart (7d, 1m, 1y)
                    percentChangeTxt.text = ticker.quotes[0].percent_change_7d.toString()
                }

                // todo: make a db query to get the chart data.
                // todo: pass in a parameter to tell whether the price data is from 7d, 1m, 1y or whatever.
                chartPlaceholderTxt.text = ;    // a list of HistoricalTicker
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): CoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinChartRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return CoinsViewHolder(binding)
            }
        }
    }

    class CoinsDiffCallback : DiffUtil.ItemCallback<Ticker>() {
        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem == newItem
        }
    }
}