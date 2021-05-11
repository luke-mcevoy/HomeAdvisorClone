package com.mcevoy.mcevoy_homeadvisor.viewmodel

import android.app.Application
import android.app.NotificationManager
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mcevoy.mcevoy_homeadvisor.model.ProAPIService
import com.mcevoy.mcevoy_homeadvisor.model.ProData
import com.mcevoy.mcevoy_homeadvisor.model.ProDatabase
import com.mcevoy.mcevoy_homeadvisor.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class ListViewModel(application: Application): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L  // 5 minutes in nanoseconds

    private val proService = ProAPIService()
    private val disposable = CompositeDisposable()

    val pros = MutableLiveData<List<ProData>>()
    val prosLoadingError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: (5 * 60)
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val pros = ProDatabase(getApplication()).proDao().getAllPros()
            prosRetrieved(pros)
            Toast.makeText(getApplication(), "Pros retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            proService.getPros()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<ProData>>() {
                    override fun onSuccess(proList: List<ProData>) {
                        storeProsLocally(proList)
                        Toast.makeText(getApplication(), "Pros retrieved from endpoint", Toast.LENGTH_SHORT).show()
                    }
                    override fun onError(e: Throwable) {
                        prosLoadingError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun prosRetrieved(proList: List<ProData>) {
        pros.value = proList
        prosLoadingError.value = false
        loading.value = false
    }


    private fun storeProsLocally(list: List<ProData>) {
        launch {
            val dao = ProDatabase(getApplication()).proDao()
            dao.deleteAllPros()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                i++
            }
            prosRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }



}