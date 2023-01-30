package com.razzagalangadzan.pklpaninti.Forecast.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.razzagalangadzan.pklpaninti.R

class ForecastHomeAdapter : RecyclerView.Adapter<ForecastHomeAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var intensity : TextView = itemView.findViewById(R.id.tvIntensity)
        var illustration : ImageView = itemView.findViewById(R.id.imageFragmentHomeIllustration)
        var time : TextView = itemView.findViewById(R.id.tvTime)
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

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ForecastHomeData = differ.currentList[position]

        holder.intensity.text = ForecastHomeData.intensity
        holder.illustration.setImageResource(ForecastHomeData.illustration)
        holder.time.text = ForecastHomeData.time
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}