package com.iverno.gustavo.movihelp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTheMovieDBClient (baseUrl: String) {
    private val retrofit = Retrofit.Builder()
                                    .baseUrl(baseUrl)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

    fun<T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}