package com.iverno.gustavo.movihelp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iverno.gustavo.movihelp.config.Constants.DB_NAME
import com.iverno.gustavo.movihelp.config.Constants.DB_VERSION
import com.iverno.gustavo.movihelp.data.TheMoviedbItem

@Database(
    entities = [TheMoviedbItem::class],
    version = DB_VERSION
)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun theMovieDBItemDao(): TheMovieDBItemDao

    companion object {
        @Volatile
        private var databseInstance: AppDatabase? = null

        fun getDatabasenIstance(mContext: Context): AppDatabase =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}

