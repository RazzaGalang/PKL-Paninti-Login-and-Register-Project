package com.razzagalangadzan.pklpaninti.Forecast.Event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.razzagalangadzan.pklpaninti.Forecast.Home.ForecastHomeData
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.ItemEventForecastBinding
import com.razzagalangadzan.pklpaninti.databinding.ItemHomeForecastBinding

class ForecastEventAdapter : RecyclerView.Adapter<ForecastEventAdapter.ViewHolder>() {

    private lateinit var binding: ItemEventForecastBinding
    private val limit = 7

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item : ForecastEventData){
            binding.apply {
                tvRvDay.text = item.day
                tvRvDescription.text = item.description
                tvRvTemperature.text = item.temperature
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ForecastEventData>(){
        override fun areItemsTheSame(
            oldItem: ForecastEventData,
            newItem: ForecastEventData
        ): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(
            oldItem: ForecastEventData,
            newItem: ForecastEventData
        ): Boolean {
            return oldItem.day == newItem.day
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > limit) limit else differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemEventForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }
}