package com.hustcaster.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedItemDao {
    @Insert
    suspend fun insertItem(item: FeedItem)

    @Update
    suspend fun updateItem(item: FeedItem)

    @Query("select * from feedItems")
    fun queryAllFeedItems(): List<FeedItem>

    @Delete
    suspend fun deleteItem(item: FeedItem)

    @Transaction
    @Query("select * from feeds where id in (select distinct(feed_id) from feedItems)")
    fun getFeedAndFeedItems(): Flow<List<FeedAndFeedItems>>
}