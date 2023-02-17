package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.ApiHelper
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.RetrofitBuilder
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Forecastday
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.base.ViewModelFactory
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter.ForecastEventAdapter
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel.MainViewModel
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.Status
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentEventForecastBinding
import java.util.*

class ForecastEventFragment : Fragment() {

    private var _binding: FragmentEventForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var adapter: ForecastEventAdapter = ForecastEventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventForecastBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
        setBackgroundGradientColor()
        backToMain()
    }

    private fun showLoading(loading: Boolean) {
        binding.animLoading.isVisible = loading
        binding.groupEventMainView.isVisible = !loading
        binding.rvWeekForecast.isVisible = !loading
    }

    private fun setBackgroundGradientColor() {
        binding.cvGradient.setBackgroundResource(R.drawable.bg_gradient_forecast)
    }

    private fun backToMain() {
        binding.icBackArrow.setOnClickListener {
            val fragment = ForecastHomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.flFragment, fragment)?.commit()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI() {
        binding.rvWeekForecast.addItemDecoration(
            DividerItemDecoration(
                binding.rvWeekForecast.context,
                (binding.rvWeekForecast.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvWeekForecast.adapter = adapter
    }

    @SuppressLint("SimpleDateFormat")
    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                showLoading(resource.status ==  Status.SUCCESS || resource.status == Status.ERROR )
                showLoading(resource.status == Status.LOADING)

                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.forecast?.forecastday?.component2()?.day.let { values ->
                            val valuesMaxTemp = values?.maxtempC.toString()
                            val valuesMinTemp = "${values?.mintempC}°"
                            val valuesDesc = values?.condition?.text
                            val valuesWind = "${values?.maxwindKph} km/h"
                            val valuesHumidity = "${values?.avghumidity}%"
                            val valuesChangeOfRain = "${values?.dailyChanceOfRain}%"
                            val getCode = values?.condition?.code

                            val viewIllustration = binding.viewIllustration
                            when (getCode){
                                1000 -> {viewIllustration.setAnimation(R.raw.ic_weather_sunny)}
                                1003, 1006 -> {viewIllustration.setAnimation(R.raw.ic_weather_partly_cloudy)}
                                1009, 1030 -> {viewIllustration.setAnimation(R.raw.ic_weather_mist)}
                                1066, 1069, 1072, 1210, 1213, 1216 -> {viewIllustration.setAnimation(R.raw.ic_weather_snow_sunny)}
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> {viewIllustration.setAnimation(R.raw.ic_weather_snow)}
                                1135, 1147 -> {viewIllustration.setAnimation(R.raw.ic_weather_windy)}
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252 -> {viewIllustration.setAnimation(R.raw.ic_weather_partly_shower)}
                                1273, 1276, 1279, 1282 -> {viewIllustration.setAnimation(R.raw.ic_weather_stormshowersday)}
                            }
                            viewIllustration.playAnimation()

                            binding.apply {
                                tvMaxTemp.text = valuesMaxTemp
                                tvMinTemp.text = valuesMinTemp
                                tvDescription.text = valuesDesc
                                tvDescWind.text = valuesWind
                                tvDescHumidity.text = valuesHumidity
                                tvDescChanceOfRain.text = valuesChangeOfRain
                            }
                        }

                        resource.data?.let { users -> adapter.items = users.forecast.forecastday }
                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        Log.e(ContentValues.TAG, "setupObservers: ${it.message}")
                    }
                    Status.LOADING -> {
                        Log.e(ContentValues.TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
    }
}