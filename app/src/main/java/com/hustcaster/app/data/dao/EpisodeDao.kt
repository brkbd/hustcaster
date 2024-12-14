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
    suspend fun insertEpisode(episodes: Episode):Long

    @Update
    suspend fun updateEpisode(vararg episodes: Episode)

    @Query("select * from episodes")
    fun queryAllEpisodes(): Flow<List<Episode>>

    @Transaction
    @Query("select * from podcasts where id in (select distinct(podcast_id) from episodes)")
    fun getPodcastAndEpisodesListFlow(): Flow<List<PodcastAndEpisodes>>

    @Query("select * from episodes where episode_id=:id")
    fun getEpisodeById(id: Long): Episode

    @Query("select image_url from podcasts where id=(select podcast_id from episodes where id=:id)")
    fun getImageUrlByEpisodeId(id: Long): String

    @Transaction
    @Query("select * from episodes where is_downloaded=1")
    fun getDownloadedEpisodes(): Flow<List<Episode>>



    @Query("select * from episodes where podcast_id=:podcastId")
    fun getEpisodes(podcastId: Long): Flow<List<Episode>>

    @Delete
    suspend fun deleteEpisode(episode: Episode)
}