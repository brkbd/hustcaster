package com.hustcaster.app.data.repository

import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.dao.EpisodeDao
import javax.inject.Singleton

@Singleton
class EpisodeRepository(
    private val episodeDao: EpisodeDao
) {
    suspend fun saveEpisode(episode: Episode) {
        episodeDao.insertEpisode(episode)
    }

    suspend fun removeEpisode(episode: Episode) {
        episodeDao.deleteEpisode(episode)
    }

    suspend fun updateEpisode(episode: Episode){
        episodeDao.updateEpisode(episode)
    }

    fun getPodcastAndEpisodes() = episodeDao.getPodcastAndEpisodesListFlow()

    fun getDownloadedEpisodes() = episodeDao.getDownloadedEpisodes()

    fun getEpisodes(podcast: Podcast) = episodeDao.getEpisodes(podcast.id)

    companion object {
        @Volatile
        private var instance: EpisodeRepository? = null

        fun getInstance(episodeDao: EpisodeDao) =
            instance ?: synchronized(this) {
                instance ?: EpisodeRepository(episodeDao).also { instance = it }
            }

    }
}