package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view

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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[MainViewModel::class.java]
    }

    private fun setupUI() {
        binding.rvWeekForecast.layoutManager = LinearLayoutManager(this.context)
        adapter = ForecastEventAdapter(arrayListOf())
        binding.rvWeekForecast.addItemDecoration(
            DividerItemDecoration(
                binding.rvWeekForecast.context,
                (binding.rvWeekForecast.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvWeekForecast.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getForecast().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvWeekForecast.visibility = View.VISIBLE
                        resource.data?.let { users -> retrieveList(users.forecast.forecastday) }
                        Log.e(ContentValues.TAG, "setupObservers: SUCCESS")
                    }
                    Status.ERROR -> {
                        binding.rvWeekForecast.visibility = View.VISIBLE
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(ContentValues.TAG, "setupObservers: ${it.message}")
                    }
                    Status.LOADING -> {
                        binding.rvWeekForecast.visibility = View.GONE
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