package com.youtubeclone.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.youtubeclone.data.model.SearchHistoryEntity

@Database(entities = [SearchHistoryEntity::class], version = 1, exportSchema = false)
abstract class YoutubeCloneDatabase() : RoomDatabase() {

    abstract val searchHistoryDao: SearchHistoryDAO

    companion object {
        private const val db_name = "youtube_clone_db"

        @Volatile
        private var INSTANCE: YoutubeCloneDatabase? = null

        fun getDBInstance(context: Context): YoutubeCloneDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
        }

        private fun buildDataBase(context: Context): YoutubeCloneDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = YoutubeCloneDatabase::class.java,
                name = db_name
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
