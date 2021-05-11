package com.mcevoy.mcevoy_homeadvisor.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProAPIService {

    private val BASE_URL = "https://luke-mcevoy.github.io/"

    private val API = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProAPI::class.java)

    fun getPros(): Single<List<ProData>> {
        return API.getProData()
    }

}