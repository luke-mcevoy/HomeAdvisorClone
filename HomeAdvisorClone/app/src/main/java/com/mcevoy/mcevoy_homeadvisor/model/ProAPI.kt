package com.mcevoy.mcevoy_homeadvisor.model

import io.reactivex.Single
import retrofit2.http.GET

interface ProAPI {
    @GET(".")
    fun getProData(): Single<List<ProData>>
}