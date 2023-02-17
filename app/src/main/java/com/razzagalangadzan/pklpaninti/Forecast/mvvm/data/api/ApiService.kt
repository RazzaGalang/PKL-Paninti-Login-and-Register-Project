package com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api

import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model.WeatherData
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.EndPoint
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoint.WEATHER_END_POINT)
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") cityName : String,
        @Query("days") day: Int,
        @Query("aqi") aqi : String,
        @Query("alerts") alerts: String
    ) : WeatherData

}