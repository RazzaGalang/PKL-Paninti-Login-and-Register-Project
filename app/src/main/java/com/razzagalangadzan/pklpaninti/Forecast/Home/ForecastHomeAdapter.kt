package com.razzagalangadzan.pklpaninti.Forecast.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.razzagalangadzan.pklpaninti.databinding.ItemHomeForecastBinding

class ForecastHomeAdapter : RecyclerView.Adapter<ForecastHomeAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeForecastBinding
    private val limit = 4

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item : ForecastHomeData){
            binding.apply {
                tvIntensity.text = item.intensity
                tvTime.text = item.time
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ForecastHomeData>(){
        override fun areItemsTheSame(
            oldItem: ForecastHomeData,
            newItem: ForecastHomeData
        ): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(
            oldItem: ForecastHomeData,
            newItem: ForecastHomeData
        ): Boolean {
            return oldItem.time == newItem.time
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHomeForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int {
        return if (differ.currentList.size > limit) limit else differ.currentList.size
    }


}