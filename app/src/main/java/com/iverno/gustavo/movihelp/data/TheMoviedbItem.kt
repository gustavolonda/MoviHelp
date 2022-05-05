package com.iverno.gustavo.movihelp.data
import java.io.Serializable
import java.lang.StringBuilder
import com.google.gson.annotations.SerializedName
import com.iverno.gustavo.movihelp.config.Constants

data class TheMoviedbItem(@SerializedName("id")            val id: Int,
                          @SerializedName("title")         val title: String,
                          @SerializedName("poster_path")   val posterPath: String?,
                          @SerializedName("backdrop_path") val backdropPath: String?,
                          @SerializedName("vote_average")  val voteAverage: Double,
                          @SerializedName("popularity")    val popularity: Double,
                          @SerializedName("overview")      val description: String,
                          @SerializedName("media_type")    val mediaType: String):
    Serializable {

    fun getBackdropUrlString(): String {
        return StringBuilder().append(Constants.IMAGE_BASE_URL)
                            .append(backdropPath)
                            .toString()
    }
    fun getPosterUrlString(): String {
        return StringBuilder().append(Constants.IMAGE_BASE_URL)
                            .append(posterPath)
                            .toString()
    }
}