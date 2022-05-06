package com.iverno.gustavo.movihelp.db

import androidx.room.*
import com.iverno.gustavo.movihelp.config.Constants.THE_MOVIE_DB_ITEM_TABLE_NAME
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
 interface TheMovieDBItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTheMoviedbItem(movie: TheMoviedbItem) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTheMoviedbItemList(movies: List<TheMoviedbItem>) : Completable

    @Query("SELECT * FROM ${THE_MOVIE_DB_ITEM_TABLE_NAME}")
    fun getAllRecords(): Single<List<TheMoviedbItem>>

    @Delete
    fun deleteTheMoviedbItem(movie:TheMoviedbItem) : Completable

    @Update
    fun updateTheMoviedbItem(movie:TheMoviedbItem)
}