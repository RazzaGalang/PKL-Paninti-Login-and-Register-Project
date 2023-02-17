package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Hour
import com.razzagalangadzan.pklpaninti.databinding.ItemHomeForecastBinding

class ForecastHomeAdapter :
    RecyclerView.Adapter<ForecastHomeAdapter.ViewHolder>() {

    inner class ViewHolder (private val binding: ItemHomeForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: Hour) {
            binding.apply {
                val valuesHumidity = "${item.humidity}%"
                val valuesHour = item.time.substring(11, 16)
                val valuesImage = "https:${item.condition.icon}"

                tvHumidity.text = valuesHumidity
                tvTime.text = valuesHour
                Glide.with(imageFragmentHomeIllustration.context)
                    .load(valuesImage)
                    .into(imageFragmentHomeIllustration)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(
            oldItem: Hour,
            newItem: Hour
        ) = oldItem.time == newItem.time

        override fun areContentsTheSame(
            oldItem: Hour,
            newItem: Hour
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var items : List<Hour>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHomeForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int = items.size
}