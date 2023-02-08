package com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.repository

import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getForecast() = apiHelper.getForecast()
}