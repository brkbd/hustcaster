package com.hustcaster.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    @Insert
    suspend fun insertFeed(podcast: Podcast)

    @Update
    suspend fun updateFeed(newPodcast: Podcast)

    @Query("select * from podcasts")
    fun queryAllFeeds(): Flow<List<Podcast>>

    @Query("select image_url from podcasts where id=:feedId")
    fun queryFeedPictureUrlById(feedId: Long): Flow<String>

    @Delete
    suspend fun deleteFeed(podcast: Podcast)
}