package com.hustcaster.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert
    suspend fun insertFeed(feed: Feed)

    @Update
    suspend fun updateFeed(newFeed: Feed)

    @Query("select * from feeds")
    fun queryAllFeeds(): Flow<List<Feed>>

    @Query("select image_url from feeds where id=:feedId")
    fun queryFeedPictureUrlById(feedId: Long): Flow<String>

    @Delete
    suspend fun deleteFeed(feed: Feed)
}