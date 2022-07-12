package com.example.cryptomarket.ui.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptomarket.data.coinsapi.ticker.Ticker
import com.example.cryptomarket.databinding.CoinChartRecyclerItemBinding
import com.example.cryptomarket.ui.CryptoViewModel
import com.example.cryptomarket.utils.DateFrame
import com.example.cryptomarket.utils.pickPercentChange

class CoinsListAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val chosenTimeFrame: DateFrame,
    private val vm: CryptoViewModel
) :
    ListAdapter<Ticker, CoinsListAdapter.CoinsViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder.from(viewLifecycleOwner, chosenTimeFrame, vm, parent)
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CoinsViewHolder private constructor(
        private val viewLifecycleOwner: LifecycleOwner,
        private val chosenTimeFrame: DateFrame,
        private val vm: CryptoViewModel,
        private val binding: CoinChartRecyclerItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(ticker: Ticker) {
            populateUI(ticker)
            binding.apply {
                itemContainer.setOnClickListener {
                    vm.setTickerClicked(ticker)
                }
                executePendingBindings()
            }
        }

        private fun populateUI(ticker: Ticker) {
            binding.apply {
                coinSymbolTxt.text = ticker.symbol
                coinNameTxt.text = ticker.name
                rankTxt.text = ticker.rank.toString()
//                if (ticker.quotes.isNotEmpty()) { todo
//                    // todo: possible bug: although in practice it's probably no problem
//                    //      the price might not be in USD (position in the list)
////                    priceTxt.text = ticker.quotes[0].price.toString() todo
////                    percentChangeTxt.text = pickPercentChange(chosenTimeFrame, ticker.quotes[0])?.toString() ?: ""    todo
//                }
                vm.getHistoricalTickerData(chosenTimeFrame).observe(viewLifecycleOwner) {
                    chartPlaceholderTxt.text = it.toString()
                }
            }
        }

        companion object {
            fun from(
                viewLifecycleOwner: LifecycleOwner,
                chosenTimeFrame: DateFrame,
                vm: CryptoViewModel,
                parent: ViewGroup
            ): CoinsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoinChartRecyclerItemBinding
                    .inflate(layoutInflater, parent, false)
                return CoinsViewHolder(viewLifecycleOwner, chosenTimeFrame, vm, binding)
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