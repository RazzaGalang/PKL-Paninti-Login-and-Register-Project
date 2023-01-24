package com.razzagalangadzan.pklpaninti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ForecastAdapter(private val mList: List<ForecastData>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_week, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ForecastData = mList[position]

        holder.day.text = ForecastData.day
        holder.illustration.setImageResource(ForecastData.illustration)
        holder.description.text = ForecastData.description
        holder.temperature.text = ForecastData.temperature
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var day : TextView = itemView.findViewById(R.id.tvRvDay)
        var illustration : ImageView = itemView.findViewById(R.id.imgRvIllustration)
        var description : TextView = itemView.findViewById(R.id.tvRvDescription)
        var temperature : TextView = itemView.findViewById(R.id.tvRvTemperature)
    }
}