package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.repository.MainRepository
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getForecast() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getForecast()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}