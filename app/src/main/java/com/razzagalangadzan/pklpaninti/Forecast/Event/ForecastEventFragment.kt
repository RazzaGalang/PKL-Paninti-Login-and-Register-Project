package com.razzagalangadzan.pklpaninti.Forecast.Event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzagalangadzan.pklpaninti.Forecast.Home.ForecastHomeFragment
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentEventForecastBinding

class ForecastEventFragment : Fragment() {

    private var _binding: FragmentEventForecastBinding? = null
    private val binding get() = _binding!!
    private val ForecastEventData by lazy { ForecastEventAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apply()
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

    private fun apply (){
        ForecastEventData.differ.submitList(loadData())

        binding.apply {
            rvWeekForecast.apply {
                layoutManager=LinearLayoutManager(this.context)
                adapter = ForecastEventData
            }
        }
    }

    fun loadData(): MutableList<ForecastEventData>{
        val data : MutableList<ForecastEventData> = mutableListOf()

        data.add(ForecastEventData("Monday", R.drawable.ic_cloud, "Cloudy", "29°"))
        data.add(ForecastEventData("Tuesday", R.drawable.ic_cloud, "Cloudy", "24°"))
        data.add(ForecastEventData("Wednesday", R.drawable.ic_rain, "Rainy", "17°"))
        data.add(ForecastEventData("Thursday", R.drawable.ic_cloud_lighting, "Storm", "18°"))
        data.add(ForecastEventData("Friday", R.drawable.ic_sun, "Sunny", "22°"))
        data.add(ForecastEventData("Saturday", R.drawable.ic_sun, "Sunny", "24°"))
        data.add(ForecastEventData("Sunday", R.drawable.ic_sun, "Sunny", "30°"))

        return data
    }
}