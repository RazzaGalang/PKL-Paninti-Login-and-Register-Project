package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.*
import com.razzagalangadzan.pklpaninti.databinding.ItemEventForecastBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ForecastEventAdapter(private var forecast: List<Forecastday>) :
    RecyclerView.Adapter<ForecastEventAdapter.ViewHolder>() {

    private val limit = 7

    inner class ViewHolder(private val binding: ItemEventForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun setData(item: Forecastday) {
            binding.apply {
                val getDate = item.date
                val defaultFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateFormat: DateFormat = SimpleDateFormat("EEEE")

                val valuesDate: String = dateFormat.format(defaultFormat.parse(getDate) as Date)
                val valuesImage = "https:${item.day.condition.icon}"
                val valuesCondition = item.day.condition.text
                val valuesTemperature = "${item.day.avgtempC}°"


                Glide.with(imgRvIllustration.context)
                    .load(valuesImage)
                    .into(imgRvIllustration)
                tvRvDay.text = valuesDate
                tvRvDescription.text = valuesCondition
                tvRvTemperature.text = valuesTemperature
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: Forecastday,
            newItem: Forecastday
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differEvent = AsyncListDiffer(this, differCallback)

    var items : List<Forecastday>
        get() = differEvent.currentList
        set(value) = differEvent.submitList(value)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(items[position])
        holder.setIsRecyclable(true)
    }

    override fun getItemCount(): Int {
        return if (items.size > limit) limit else items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemEventForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}