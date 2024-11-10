package com.hustcaster.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    @Insert
    suspend fun insertEpisode(episode: Episode)

    @Update
    suspend fun updateEpisode(episode: Episode)

    @Query("select * from episodes")
    fun queryAllEpisodes(): Flow<List<Episode>>

    @Transaction
    @Query("select * from podcasts where id in (select distinct(podcast_id) from episodes)")
    fun getPodcastAndEpisodes(): Flow<List<PodcastAndEpisodes>>

    @Transaction
    @Query("select * from episodes where is_downloaded=1")
    fun getDownloadedEpisodes(): Flow<List<Episode>>

    @Transaction
    @Query("select * from episodes where is_played=1")
    fun getPlayedEpisodes(): Flow<List<Episode>>

    @Query("select * from episodes where is_updated=1")
    fun getUpdatedEpisodes(): Flow<List<Episode>>

    @Query("select * from episodes where podcast_id=:podcastId")
    fun getEpisodes(podcastId: Long): Flow<List<Episode>>

    @Delete
    suspend fun deleteEpisode(episode: Episode)
}