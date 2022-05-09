package com.iverno.gustavo.movihelp.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.lang.StringBuilder
import com.google.gson.annotations.SerializedName
import com.iverno.gustavo.movihelp.config.Constants.THE_MOVIE_DB_ITEM_TABLE_NAME
import com.iverno.gustavo.movihelp.config.Constants.IMAGE_BASE_URL
@Entity(tableName = THE_MOVIE_DB_ITEM_TABLE_NAME)
data class TheMoviedbItem(@PrimaryKey @SerializedName(ID)            val id: Int,
                          @ColumnInfo(name = TITLE)  @SerializedName(TITLE)           val title: String,
                          @ColumnInfo(name = NAME)   @SerializedName(NAME)             val name: String,
                          @ColumnInfo(name = POSTER_PATH) @SerializedName(POSTER_PATH)   val posterPath: String?,
                          @ColumnInfo(name = BACKDROP_PATH) @SerializedName(BACKDROP_PATH) val backdropPath: String?,
                          @ColumnInfo(name = VOTE_AVERAGE)  @SerializedName(VOTE_AVERAGE)  val voteAverage: Double,
                          @ColumnInfo(name = POPULARITY)    @SerializedName(POPULARITY)    val popularity: Double,
                          @ColumnInfo(name = DESCRIPTION)   @SerializedName(OVERVIEW)      val description: String,
                          @ColumnInfo(name = MEDIA_TYPE)    @SerializedName(MEDIA_TYPE)    val mediaType: String):
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
    companion object {
        const val  ID           = "id"
        const val  TITLE        = "title"
        const val  NAME         = "name"
        const val  DESCRIPTION  = "description"
        const val  POPULARITY   = "popularity"
        const val  VOTE_AVERAGE = "vote_average"
        const val  MEDIA_TYPE   = "media_type"
        const val  OVERVIEW     = "overview"
        const val  POSTER_PATH  = "poster_path"
        const val  BACKDROP_PATH  = "backdrop_path"




    }
}