package com.razzagalangadzan.pklpaninti.Forecast.Event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.razzagalangadzan.pklpaninti.R

class ForecastEventAdapter : RecyclerView.Adapter<ForecastEventAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var day : TextView = itemView.findViewById(R.id.tvRvDay)
        var illustration : ImageView = itemView.findViewById(R.id.imgRvIllustration)
        var description : TextView = itemView.findViewById(R.id.tvRvDescription)
        var temperature : TextView = itemView.findViewById(R.id.tvRvTemperature)
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

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ForecastData = differ.currentList[position]

        holder.day.text = ForecastData.day
        holder.illustration.setImageResource(ForecastData.illustration)
        holder.description.text = ForecastData.description
        holder.temperature.text = ForecastData.temperature
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_week, parent, false)

        return ViewHolder(view)
    }
}