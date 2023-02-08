package com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.api.ApiHelper
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.data.repository.MainRepository
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.viewmodel.MainViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}