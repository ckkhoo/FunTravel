package com.example.localguide

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CountryViewModel(application: Application) : AndroidViewModel(application){

    val countryLive: MutableLiveData<Country> by lazy {
        MutableLiveData<Country>()
    }
}