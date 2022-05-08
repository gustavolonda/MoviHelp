package com.iverno.gustavo.movihelp.repository

import android.content.Context
import android.util.Log
import com.iverno.gustavo.movihelp.api.ApiTheMovieDBClient
import com.iverno.gustavo.movihelp.api.ApiTheMovieDBService
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.ERROR
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.config.Constants.ID_SELECTED_LIST
import com.iverno.gustavo.movihelp.config.Constants.BEARER
import com.iverno.gustavo.movihelp.config.Constants.TOKEN
import com.iverno.gustavo.movihelp.config.Constants.API_BASE_URL
import com.iverno.gustavo.movihelp.data.TheMovieDBListViewModelResponse
import com.iverno.gustavo.movihelp.util.GeneralUtil
import java.lang.Exception

class TheMovieDBRepository {
     companion object {
         @JvmStatic
         fun getTheMovieDBItemList(context: Context, page:Int): TheMovieDBListViewModelResponse {
             val apiTheMovieDBService: ApiTheMovieDBService = ApiTheMovieDBClient(API_BASE_URL).buildService(ApiTheMovieDBService::class.java)
             lateinit var theMovieDBListViewModelResponse: TheMovieDBListViewModelResponse
            if (!GeneralUtil.checkForInternet(context)){
                val errorMessage = "Please check the internet connection"
                theMovieDBListViewModelResponse = TheMovieDBListViewModelResponse.Builder()
                                                                .status(ERROR)
                                                                .errorMessage(errorMessage)
                                                                .build()
                return theMovieDBListViewModelResponse

            }
            val call = apiTheMovieDBService.getList(BEARER + TOKEN,
                ID_SELECTED_LIST,
                page)

            val errorMessage = "An error occurred while connecting to the server"
            try{
                val response = call.execute()
                if (response.isSuccessful){
                    theMovieDBListViewModelResponse = TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(SUCCESSFUL)
                                                                                    .theMovieDBItemList(response.body()?.theMovieDBItemList)
                                                                                    .currentPage(page)
                                                                                    .totalPages(response.body()?.totalPages)
                                                                                    .totalResults(response.body()?.totalResults)
                                                                                    .build()
                    return theMovieDBListViewModelResponse
                }
                Log.e("Gus", response.message())

                theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                                                                    .status(ERROR)
                                                                    .errorMessage(errorMessage)
                                                                    .build()
                return theMovieDBListViewModelResponse
            }catch(e: Exception){
                Log.e("Gus", e.stackTraceToString())
                theMovieDBListViewModelResponse = TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(ERROR)
                                                                                    .errorMessage(errorMessage)
                                                                                    .build()
                return      theMovieDBListViewModelResponse
            }
        }


    }


}