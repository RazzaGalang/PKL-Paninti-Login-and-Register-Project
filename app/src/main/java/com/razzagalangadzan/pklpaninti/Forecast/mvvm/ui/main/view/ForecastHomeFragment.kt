package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
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
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Hour
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.base.ViewModelFactory
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel.MainViewModel
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.Status
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentHomeForecastBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ForecastHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeForecastBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ForecastHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeForecastBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
        setBackgroundGradientColor()
        binding()
    }

    private fun setBackgroundGradientColor() {
        binding.cvGradient.setBackgroundResource(R.drawable.bg_gradient_forecast)
    }

    private fun binding() {
        binding.tvToFragment.setOnClickListener {
            val fragment = ForecastEventFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.flFragment, fragment)?.commit()
        }
    }


    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> binding.animLoading.visibility = View.VISIBLE
            false -> binding.animLoading.visibility = View.GONE
        }
    }

    private fun showHomeMainView(loading: Boolean) {
        when (loading) {
            true -> binding.groupHomeMainView.visibility = View.VISIBLE
            false -> binding.groupHomeMainView.visibility = View.GONE
        }
    }

    private fun showHomeRecyclerView(loading: Boolean) {
        when (loading) {
            true -> binding.groupHomeRecyclerView.visibility = View.VISIBLE
            false -> binding.groupHomeRecyclerView.visibility = View.GONE
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI() {
        binding.rvHomeForecast.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastHomeAdapter()
        binding.rvHomeForecast.addItemDecoration(
            DividerItemDecoration(
                binding.rvHomeForecast.context,
                (binding.rvHomeForecast.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvHomeForecast.adapter = adapter
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        val valuesLocation = "${resource.data?.location?.name}"
                        val valuesCurrentTemp = "${resource.data?.current?.tempC}°"
                        val valuesDesc = "${resource.data?.current?.condition?.text}"
                        val valueWind = "${resource.data?.current?.windKph.toString()} km/h"
                        val valueHumidity = "${resource.data?.current?.humidity}%"
                        val valueChangeOfRain = "${resource.data?.forecast?.forecastday?.component1()?.day?.dailyChanceOfRain}%"

                        val getDate = "${resource.data?.location?.localtime}"
                        val defaultFormatDate: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val dateFormat: DateFormat = SimpleDateFormat("EEEE, dd MMMM")
                        val valuesDate: String = dateFormat.format(defaultFormatDate.parse(getDate) as Date)

                        val getTime = resource.data?.current?.lastUpdated?.substring(11, 13)?.toInt()
                        val valuesCode = resource.data?.current?.condition?.code
                        var timeCondition = ""

                        when (getTime){
                            in 6..18-> timeCondition = "Day"
                            in 19..24, in 0..5 -> timeCondition = "Night"
                        }

                        val viewIllustration = binding.viewIllustration
                        if (timeCondition == "Day"){
                            when (valuesCode){
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
                        } else if (timeCondition == "Night") {
                            when (valuesCode){
                                1000 -> {viewIllustration.setAnimation(R.raw.ic_weather_night)}
                                1003, 1006 -> {viewIllustration.setAnimation(R.raw.ic_weather_cloudynight)}
                                1009, 1030 -> {viewIllustration.setAnimation(R.raw.ic_weather_mist)}
                                1066, 1069, 1072, 1210, 1213, 1216 -> {viewIllustration.setAnimation(R.raw.ic_weather_snownight)}
                                1114, 1117, 1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> {viewIllustration.setAnimation(R.raw.ic_weather_snow)}
                                1135, 1147 -> {viewIllustration.setAnimation(R.raw.ic_weather_windy)}
                                1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189,
                                1192, 1195, 1198, 1201, 1204, 1207, 1240, 1243, 1246,
                                1249, 1252-> {viewIllustration.setAnimation(R.raw.ic_weather_rainynight)}
                                1273, 1276, 1279, 1282 -> {viewIllustration.setAnimation(R.raw.ic_weather_storm)}
                            }
                            viewIllustration.playAnimation()
                        }

                        binding.tvLocation.text = valuesLocation
                        binding.tvCurrentTemp.text = valuesCurrentTemp
                        binding.tvCurrentSituation.text = valuesDesc
                        binding.tvCurrentDate.text = valuesDate
                        binding.tvDescWind.text = valueWind
                        binding.tvDescHumidity.text = valueHumidity
                        binding.tvDescChanceOfRain.text = valueChangeOfRain

                        showLoading(false)
                        showHomeMainView(true)
                        showHomeRecyclerView(true)

                        resource.data?.let { users -> retrieveList(users.forecast.forecastday.component1().hour) }

                        Log.e(TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        showLoading(true)
                        showHomeMainView(false)
                        showHomeRecyclerView(false)

                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(TAG, "setupObservers: " + it.message)
                    }
                    Status.LOADING -> {
                        showLoading(true)
                        showHomeMainView(false)
                        showHomeRecyclerView(false)

                        Log.e(TAG, "setupObservers: LOADING")
                    }
                }
            }
        })

    }

    private fun retrieveList(users: List<Hour>) {
        adapter.apply {
            this.items = users
        }
    }
}