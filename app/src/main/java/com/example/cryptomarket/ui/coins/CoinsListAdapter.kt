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
import com.example.cryptomarket.utils.presentPriceFormatUSD
import com.example.cryptomarket.utils.removeTrailing2Zeros

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
    ) : RecyclerView.ViewHolder(binding.root) {

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
                rankTxt.text = removeTrailing2Zeros(ticker.rank.toString())
                priceTxt.text = presentPriceFormatUSD("", ticker.quotes.usd.price)

                // todo: test if the time frame changes in the list when i click the time frame btns
                timeFrame.text = chosenTimeFrame.abbrev
                percentChangeTxt.text = pickPercentChange(chosenTimeFrame, ticker.quotes.usd)?.toString() ?: ""
                chartPlaceholderTxt.text = "none"
                vm.getHistoricalTickerData(false, ticker.id, chosenTimeFrame)
                    .observe(viewLifecycleOwner) {
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