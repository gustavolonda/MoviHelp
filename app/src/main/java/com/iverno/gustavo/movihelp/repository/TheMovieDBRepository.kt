package com.iverno.gustavo.movihelp.repository

import android.content.Context
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.iverno.gustavo.movihelp.api.ApiTheMovieDBClient
import com.iverno.gustavo.movihelp.api.ApiTheMovieDBService
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.ERROR
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.config.Constants
import com.iverno.gustavo.movihelp.config.Constants.ID_SELECTED_LIST
import com.iverno.gustavo.movihelp.config.Constants.BEARER
import com.iverno.gustavo.movihelp.config.Constants.TOKEN
import com.iverno.gustavo.movihelp.config.Constants.API_BASE_URL
import com.iverno.gustavo.movihelp.data.TheMovieCategoryDomain.POPULARY
import com.iverno.gustavo.movihelp.data.TheMovieCategoryDomain.TOP_RATE
import com.iverno.gustavo.movihelp.data.TheMovieDBListViewModelResponse
import com.iverno.gustavo.movihelp.data.TheMovieTypeDomain.MOVIE
import com.iverno.gustavo.movihelp.data.TheMovieTypeDomain.MOVIE_RESPONSE
import com.iverno.gustavo.movihelp.data.TheMovieTypeDomain.SERIE
import com.iverno.gustavo.movihelp.data.TheMovieTypeDomain.SERIE_RESPONSE
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import com.iverno.gustavo.movihelp.util.GeneralUtil
import com.iverno.gustavo.movihelp.util.QueryGenerator
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
                    var responseBody= response.body()
                    var totalPages= responseBody!!.totalPages
                    var totalResults= responseBody!!.totalResults
                    theMovieDBListViewModelResponse = TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(SUCCESSFUL)
                                                                                    .theMovieDBItemList(response.body()?.theMovieDBItemList)
                                                                                    .currentPage(page)
                                                                                    .totalPages(totalPages)
                                                                                    .totalResults(totalResults)
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
         @JvmStatic
         fun getSearchQuery( textSearch: String, type: String, category: String):SimpleSQLiteQuery{
             var query = QueryGenerator()
             query     = query.selecAllFrom()
                 .tablaName(Constants.THE_MOVIE_DB_ITEM_TABLE_NAME)

             if (textSearch != null && !textSearch.isEmpty()){
                 query = query.where()
                     .openParenthesis()
                     .fieldName(TheMoviedbItem.NAME)
                     .contains(textSearch)
                     .or()
                     .fieldName(TheMoviedbItem.TITLE)
                     .contains(textSearch)
                     .closeParenthesis()
             }

             var typeSearch = ""
             if (type != null && !type.isEmpty()){
                 if (!textSearch.isEmpty())
                     query = query.and()
                 else
                     query = query.where()
                 when (type) {
                     MOVIE_RESPONSE -> {typeSearch = MOVIE}
                     SERIE_RESPONSE -> {typeSearch = SERIE}
                     }


                 query = query.fieldName(TheMoviedbItem.MEDIA_TYPE)
                     .contains(typeSearch)
             }

             if (category != null && !category.isEmpty()){
                 when (category) {
                     POPULARY -> {
                                 query = query.orderBy()
                                     .fieldName(TheMoviedbItem.POPULARITY)
                                     .desc()
                        }
                     TOP_RATE -> {
                                 query = query.orderBy()
                                     .fieldName(TheMoviedbItem.VOTE_AVERAGE)
                                     .desc()
                        }

                     }


             }
             Log.e("GusQuery",query.toString())

             var simpleSQLiteQuery = SimpleSQLiteQuery(query.toString())
             return simpleSQLiteQuery
         }
    }


}