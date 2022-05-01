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
                          @SerializedName("genre_ids")     val genderIDs: List<Int>,
                          @SerializedName("overview")      val description: String,
                          @SerializedName("media_type")    val mediaType: String):
    Serializable {

    val genders: List<String> = mutableListOf()

    fun getGenderString(): String {
        val stringBuilder = StringBuilder()
        for (gender in genders) {
            stringBuilder.append(gender)
            stringBuilder.append(", ")
        }
        return stringBuilder.toString().removeSuffix(", ")
    }
    fun getbackdropUrlString(): String {
        return StringBuilder().append(Constants.IMAGE_BASE_URL)
                            .append(backdropPath)
                            .toString()
    }
    fun getPosterPathUrlString(): String {
        return StringBuilder().append(Constants.IMAGE_BASE_URL)
            .append(posterPath)
            .toString()
    }
}