package com.razzagalangadzan.pklpaninti.Forecast.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzagalangadzan.pklpaninti.Forecast.Event.ForecastEventFragment
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentHomeForecastBinding

class ForecastHomeFragment : Fragment() {

    private var _binding: FragmentHomeForecastBinding? = null
    private val binding get() = _binding!!
    private val ForecastHomeData by lazy { ForecastHomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackgroundGradientColor()
        apply()
        binding()
    }

    private fun setBackgroundGradientColor () {
        binding.cvGradient.setBackgroundResource(R.drawable.bg_gradient_forecast)
    }

    private fun binding (){
        binding.tvToFragment.setOnClickListener {
            val fragment = ForecastEventFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.flFragment, fragment)?.commit()
        }
    }

    private fun apply (){
        ForecastHomeData.differ.submitList(loadData())

        binding.apply {
            rvHomeForecast.apply {
                layoutManager=LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ForecastHomeData
            }
        }
    }

    fun loadData(): MutableList<ForecastHomeData>{
        val data : MutableList<ForecastHomeData> = mutableListOf()

        data.add(ForecastHomeData(getString(R.string.dummy_current_temp), R.drawable.ic_cloud,getString(R.string.dummy_time)))
        data.add(ForecastHomeData(getString(R.string.dummy_current_temp), R.drawable.ic_rain,getString(R.string.dummy_time)))
        data.add(ForecastHomeData(getString(R.string.dummy_current_temp), R.drawable.ic_sun,getString(R.string.dummy_time)))
        data.add(ForecastHomeData(getString(R.string.dummy_current_temp), R.drawable.ic_huge_cloud,getString(R.string.dummy_time)))

        return data
    }

}