package com.iverno.gustavo.movihelp.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.lang.StringBuilder
import com.google.gson.annotations.SerializedName
import com.iverno.gustavo.movihelp.config.Constants.THE_MOVIE_DB_ITEM_TABLE_NAME
import com.iverno.gustavo.movihelp.config.Constants.IMAGE_BASE_URL
@Entity(tableName = THE_MOVIE_DB_ITEM_TABLE_NAME)
data class TheMoviedbItem(@PrimaryKey @SerializedName("id")            val id: Int,
                          @SerializedName("title")         val title: String,
                          @SerializedName("name")         val name: String,
                          @SerializedName("poster_path")   val posterPath: String?,
                          @SerializedName("backdrop_path") val backdropPath: String?,
                          @SerializedName("vote_average")  val voteAverage: Double,
                          @SerializedName("popularity")    val popularity: Double,
                          @SerializedName("overview")      val description: String,
                          @SerializedName("media_type")    val mediaType: String):
    Serializable {
    constructor()
            : this(0, "", "", "","", 0.0, 0.0, "", "")

    fun getBackdropUrlString(): String {
        return StringBuilder().append(IMAGE_BASE_URL)
                            .append(backdropPath)
                            .toString()
    }
    fun getPosterUrlString(): String {
        return StringBuilder().append(IMAGE_BASE_URL)
                            .append(posterPath)
                            .toString()
    }
    fun getTheMovieTypeString(): String {
        if (mediaType == TheMovieTypeDomain.MOVIE)
            return TheMovieTypeDomain.MOVIE_RESPONSE
        return TheMovieTypeDomain.SERIE_RESPONSE
    }
    fun getTitleOrNameString(): String {
        if (mediaType == TheMovieTypeDomain.MOVIE)
            return this.title
        return this.name
    }
    fun getVoteAverageString(): String {
        return voteAverage.toString()
    }
    fun getPopularityString(): String {
        return popularity.toString()
    }
}