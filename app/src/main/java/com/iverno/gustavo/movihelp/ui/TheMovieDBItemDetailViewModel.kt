package com.iverno.gustavo.movihelp.ui

import com.iverno.gustavo.movihelp.config.Constants
import com.iverno.gustavo.movihelp.data.TheMovieTypeDomain
import java.io.Serializable
import java.lang.StringBuilder

data class TheMovieDBItemDetailViewModel (val title: String,
                                          val backdropUrl: String?,
                                          val voteAverage: Double,
                                          val popularity: Double,
                                          val mediaType: String):
    Serializable {

    fun getTheMovieTypeString(): String {
        if (mediaType == TheMovieTypeDomain.MOVIE)
            return TheMovieTypeDomain.MOVIE_RESPONSE
        return TheMovieTypeDomain.SERIE_RESPONSE
    }
    fun getVoteAverageString(): String {
        return voteAverage.toString()
    }
    fun getPopularityString(): String {
        return popularity.toString()
    }

}