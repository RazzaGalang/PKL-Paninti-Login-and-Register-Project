package com.razzagalangadzan.pklpaninti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzagalangadzan.pklpaninti.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {


    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setBackgroundGradientColor()
    }

    private fun setBackgroundGradientColor () {
        binding.cvGradient.setBackgroundResource(R.drawable.bg_gradient_forecast)
    }

    private fun setAdapter (){
        val recyclerview = binding.rvWeekForecast

        recyclerview.layoutManager = LinearLayoutManager(this.context)

        val data = ArrayList<ForecastData>()

        data.add(ForecastData("Monday", R.drawable.ic_cloud, "Cloudy", "29°"))
        data.add(ForecastData("Tuesday", R.drawable.ic_cloud, "Cloudy", "24°"))
        data.add(ForecastData("Wednesday", R.drawable.ic_rain, "Rainy", "17°"))
        data.add(ForecastData("Thursday", R.drawable.ic_cloud_lighting, "Storm", "18°"))
        data.add(ForecastData("Friday", R.drawable.ic_sun, "Sunny", "22°"))
        data.add(ForecastData("Saturday", R.drawable.ic_sun, "Sunny", "24°"))
        data.add(ForecastData("Sunday", R.drawable.ic_sun, "Sunny", "30°"))

        val adapter = ForecastAdapter(data)

        recyclerview.adapter = adapter
    }
}