package com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.model


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: List<Forecastday>
)