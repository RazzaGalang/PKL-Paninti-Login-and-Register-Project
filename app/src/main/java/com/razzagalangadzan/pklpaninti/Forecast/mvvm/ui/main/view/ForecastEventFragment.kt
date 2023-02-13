package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter.ForecastHomeAdapter
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.ApiHelper
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.RetrofitBuilder
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Forecastday
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Hour
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.base.ViewModelFactory
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter.ForecastEventAdapter
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel.MainViewModel
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.Status
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentEventForecastBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ForecastEventFragment : Fragment() {

    private var _binding: FragmentEventForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ForecastEventAdapter

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
        setupViewModel()
        setupUI()
        setupObservers()
        setBackgroundGradientColor()
        backToMain()
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> binding.animLoading.visibility = View.VISIBLE
            false -> binding.animLoading.visibility = View.GONE
        }
    }

    private fun showEventMainView(loading: Boolean) {
        when (loading) {
            true -> binding.groupEventMainView.visibility = View.VISIBLE
            false -> binding.groupEventMainView.visibility = View.GONE
        }
    }

    private fun showEventRecyclerView(loading: Boolean) {
        when (loading) {
            true -> binding.rvWeekForecast.visibility = View.VISIBLE
            false -> binding.rvWeekForecast.visibility = View.GONE
        }
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
        binding.rvWeekForecast.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        adapter = ForecastEventAdapter(arrayListOf())
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
                when (resource.status) {
                    Status.SUCCESS -> {
                        val valuesMaxTemp = "${resource.data?.forecast?.forecastday?.component2()?.day?.maxtempC}"
                        val valuesMinTemp = "${resource.data?.forecast?.forecastday?.component2()?.day?.mintempC}°"
                        val valuesDesc = resource.data?.forecast?.forecastday?.component2()?.day?.condition?.text
                        val valuesWind = "${resource.data?.forecast?.forecastday?.component2()?.day?.maxwindKph} km/h"
                        val valuesHumidity = "${resource.data?.forecast?.forecastday?.component2()?.day?.avghumidity}%"
                        val valuesChangeOfRain = "${resource.data?.forecast?.forecastday?.component2()?.day?.dailyChanceOfRain}%"

                        val getCode = resource.data?.forecast?.forecastday?.component2()?.day?.condition?.code

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

                        binding.tvMaxTemp.text = valuesMaxTemp
                        binding.tvMinTemp.text = valuesMinTemp
                        binding.tvDescription.text = valuesDesc
                        binding.tvDescWind.text = valuesWind
                        binding.tvDescHumidity.text = valuesHumidity
                        binding.tvDescChanceOfRain.text = valuesChangeOfRain

                        showLoading(false)
                        showEventMainView(true)
                        showEventRecyclerView(true)


                        resource.data?.let { users -> retrieveList(users.forecast.forecastday) }
                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        showLoading(true)
                        showEventMainView(false)
                        showEventRecyclerView(false)

                        Log.e(ContentValues.TAG, "setupObservers: ${it.message}")
                    }
                    Status.LOADING -> {
                        showLoading(true)
                        showEventMainView(false)
                        showEventRecyclerView(false)

                        Log.e(ContentValues.TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<Forecastday>) {
        adapter.apply {
            this.items = users
        }
    }
}