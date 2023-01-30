package com.razzagalangadzan.pklpaninti.Forecast.Event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzagalangadzan.pklpaninti.Forecast.Home.ForecastHomeFragment
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentForecastBinding


class ForecastEventFragment : Fragment() {


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
        backToMain()
    }

    private fun setBackgroundGradientColor () {
        binding.cvGradient.setBackgroundResource(R.drawable.bg_gradient_forecast)
    }

    private fun backToMain(){
        binding.icBackArrow.setOnClickListener {
            val fragment = ForecastHomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.flFragment, fragment)?.commit()
        }
    }

    private fun setAdapter (){
        val recyclerview = binding.rvWeekForecast

        recyclerview.layoutManager = LinearLayoutManager(this.context)

        val data = ArrayList<ForecastEventData>()

        data.add(ForecastEventData("Monday", R.drawable.ic_cloud, "Cloudy", "29°"))
        data.add(ForecastEventData("Tuesday", R.drawable.ic_cloud, "Cloudy", "24°"))
        data.add(ForecastEventData("Wednesday", R.drawable.ic_rain, "Rainy", "17°"))
        data.add(ForecastEventData("Thursday", R.drawable.ic_cloud_lighting, "Storm", "18°"))
        data.add(ForecastEventData("Friday", R.drawable.ic_sun, "Sunny", "22°"))
        data.add(ForecastEventData("Saturday", R.drawable.ic_sun, "Sunny", "24°"))
        data.add(ForecastEventData("Sunday", R.drawable.ic_sun, "Sunny", "30°"))

        val adapter = ForecastEventAdapter(data)

        recyclerview.adapter = adapter
    }
}