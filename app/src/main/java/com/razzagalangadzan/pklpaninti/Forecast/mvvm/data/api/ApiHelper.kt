package com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api

class ApiHelper(private val apiService: ApiService) {

    companion object {
        private const val API_KEY = "191e4e32fec14db5ac111951233101"
    }

    suspend fun getForecast() = apiService.getForecast(API_KEY, "Bandung", 7, "no", "no")
}