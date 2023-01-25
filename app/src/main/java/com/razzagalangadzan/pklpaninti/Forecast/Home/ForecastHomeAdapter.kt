package com.razzagalangadzan.pklpaninti.Forecast.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.razzagalangadzan.pklpaninti.R

class ForecastHomeAdapter(private val mList: List<ForecastHomeData>) : RecyclerView.Adapter<ForecastHomeAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast_home, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ForecastHomeData = mList[position]

        holder.intensity.text = ForecastHomeData.intensity
        holder.illustration.setImageResource(ForecastHomeData.illustration)
        holder.time.text = ForecastHomeData.time
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var intensity : TextView = itemView.findViewById(R.id.tvIntensity)
        var illustration : ImageView = itemView.findViewById(R.id.imageFragmentHomeIllustration)
        var time : TextView = itemView.findViewById(R.id.tvTime)
    }
}