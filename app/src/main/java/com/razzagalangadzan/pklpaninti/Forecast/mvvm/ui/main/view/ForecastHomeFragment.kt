package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view

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
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.adapter.ForecastHomeAdapter
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.ApiHelper
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.RetrofitBuilder
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Current
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Forecast
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.Hour
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.WeatherData
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.base.ViewModelFactory
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel.MainViewModel
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.Status
import com.razzagalangadzan.pklpaninti.R
import com.razzagalangadzan.pklpaninti.databinding.FragmentHomeForecastBinding
import kotlin.math.log

class ForecastHomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeForecastBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ForecastHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeForecastBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
        setBackgroundGradientColor()
        binding()
//        applyNonRecycler()
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

    private fun applyNonRecycler(item: Current) {
        binding.tvDescWind.text = item.windKph.toString()
        binding.tvDescHumidity.text = item.humidity.toString()
        binding.tvDescChanceOfRain.text = item.cloud.toString()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI() {
        binding.rvHomeForecast.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastHomeAdapter()
        binding.rvHomeForecast.addItemDecoration(
            DividerItemDecoration(
                binding.rvHomeForecast.context,
                (binding.rvHomeForecast.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvHomeForecast.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvHomeForecast.visibility = View.VISIBLE
                        resource.data?.let { users -> retrieveList(users.forecast.forecastday.component1().hour) }
                        Log.e(TAG, "setupObservers: SUCCESS")
                        Log.e(TAG, "${resource.data}")
                    }
                    Status.ERROR -> {
                        binding.rvHomeForecast.visibility = View.VISIBLE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(TAG, "setupObservers: " + it.message)
                    }
                    Status.LOADING -> {
                        binding.rvHomeForecast.visibility = View.GONE
                        Log.e(TAG, "setupObservers: LOADING")
                    }
                }
            }
        })
    }

    private fun retrieveList(users: List<Hour>) {
        adapter.apply {
            this.items = users
            Log.e(TAG, "retrieveList : ${users}")
        }
    }
}