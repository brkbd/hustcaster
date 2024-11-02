package com.hustcaster.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FeedItemDao {
    @Insert
    suspend fun insertItem(item: FeedItem)

    @Update
    suspend fun updateItem(item: FeedItem)

    @Query("select * from feedItems")
    fun queryAllFeedItems():List<FeedItem>

    @Delete
    suspend fun deleteItem(item: FeedItem)
}