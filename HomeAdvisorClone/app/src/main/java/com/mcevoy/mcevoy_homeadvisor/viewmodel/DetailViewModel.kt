package com.mcevoy.mcevoy_homeadvisor.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mcevoy.mcevoy_homeadvisor.model.ProData
import com.mcevoy.mcevoy_homeadvisor.model.ProDatabase
import kotlinx.coroutines.launch


class DetailViewModel(application: Application): BaseViewModel(application) {

    val proLiveData = MutableLiveData<ProData>()

    fun fetch(uuid: Int) {
        launch {
            val pro = ProDatabase(getApplication()).proDao().getPro(uuid)
            proLiveData.value = pro
        }
    }

    fun displayPhoneEmail(contactInfo: String) {
        Toast.makeText(getApplication(), contactInfo, Toast.LENGTH_LONG).show()
    }
}