package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creator_history")
data class CreatorHistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "title", "hook", "script", "topic", "competitor", "retention", "analysis"
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val extraData: String = "" // For JSON or other structured info
)
