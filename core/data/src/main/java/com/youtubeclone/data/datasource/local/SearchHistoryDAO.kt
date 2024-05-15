package com.youtubeclone.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youtubeclone.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT text FROM search_history WHERE text LIKE :keyword || '%' ORDER BY id DESC LIMIT 5")
    fun getSearchHistory(keyword: String): Flow<List<String>>

}