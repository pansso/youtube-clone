package com.youtubeclone.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youtubeclone.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity)

//    @Query("SELECT DISTINCT text FROM search_history WHERE text LIKE '%' || :keyword || '%' ORDER BY id DESC LIMIT 5")
//    fun getSearchHistory(keyword: String): Flow<List<String>>

    @Query("SELECT text FROM search_history ORDER BY id DESC")
    fun getAllSearchHistory(): Flow<List<String>>

    @Query("DELETE FROM search_history WHERE text = :text")
    fun deleteSearchHistory(text: String)

}