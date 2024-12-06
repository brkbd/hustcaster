package com.hustcaster.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.PodcastAndEpisodes
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    @Insert
    suspend fun insertPodcast(podcast: Podcast)

    @Update
    suspend fun updatePodcast(newPodcast: Podcast)

    @Transaction
    @Query("select * from podcasts")
    fun queryAllPodcasts(): Flow<List<PodcastAndEpisodes>>

    @Transaction
    @Query("select * from podcasts where id=:id")
    fun getPodcastAndEpisodesById(id: Long): PodcastAndEpisodes

    @Query("select * from podcasts where id=:id")
    fun getPodcastById(id: Long): Podcast

    @Query("select id from podcasts where rss_url=:url")
    fun getPodcastIdByRssUrl(url: String): Long

    @Query("select image_url from podcasts where id=:podcastId")
    fun getPodcastPictureUrlById(podcastId: Long): Flow<String>

    @Delete
    suspend fun deletePodcast(podcast: Podcast)
}