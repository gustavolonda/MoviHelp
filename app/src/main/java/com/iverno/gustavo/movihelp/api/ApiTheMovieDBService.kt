package com.iverno.gustavo.movihelp.api

import com.iverno.gustavo.movihelp.bo.ResultResponse
import com.iverno.gustavo.movihelp.config.Constants.URL_LIST_THE_MOVIE_DB
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTheMovieDBService {
    @GET(URL_LIST_THE_MOVIE_DB)
    fun getList(
                @Header("Authorization") auth: String,
                @Path("list_id") listId: Int,
                @Query("page") page: Int
    ): Call<ResultResponse>
}