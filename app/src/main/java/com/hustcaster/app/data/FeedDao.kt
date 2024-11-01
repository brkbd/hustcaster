package com.hustcaster.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FeedDao {
    @Insert
    fun insertFeed(feed: Feed)

    @Update
    fun updateFeed(newFeed: Feed)

    @Query("select * from Feed")
    fun queryAllFeeds(): List<Feed>

    @Delete
    fun deleteFeed(feed: Feed)
}