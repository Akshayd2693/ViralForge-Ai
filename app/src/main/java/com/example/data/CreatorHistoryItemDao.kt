package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatorHistoryItemDao {
    @Query("SELECT * FROM creator_history ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<CreatorHistoryItem>>

    @Query("SELECT * FROM creator_history WHERE type = :type ORDER BY timestamp DESC")
    fun getItemsByType(type: String): Flow<List<CreatorHistoryItem>>

    @Query("SELECT * FROM creator_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavorites(): Flow<List<CreatorHistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CreatorHistoryItem): Long

    @Update
    suspend fun updateItem(item: CreatorHistoryItem)

    @Delete
    suspend fun deleteItem(item: CreatorHistoryItem)

    @Query("DELETE FROM creator_history WHERE id = :id")
    suspend fun deleteItemById(id: Int)

    @Query("UPDATE creator_history SET isFavorite = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFav: Boolean)
}
