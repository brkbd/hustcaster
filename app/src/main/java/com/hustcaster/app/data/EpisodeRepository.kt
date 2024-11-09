package com.hustcaster.app.data

import javax.inject.Singleton

@Singleton
class EpisodeRepository(
    private val episodeDao: EpisodeDao
) {
    suspend fun saveFeedItem(episode: Episode) {
        episodeDao.insertItem(episode)
    }

    suspend fun removeFeedItem(episode: Episode) {
        episodeDao.deleteItem(episode)
    }

    suspend fun updateFeedItem(episode: Episode){
        episodeDao.updateItem(episode)
    }

    fun getFeedAndFeedItems() = episodeDao.getFeedAndFeedItems()

    fun getDownloadedFeedItems() = episodeDao.getDownloadedFeedItems()

    fun getPlayedFeedItems() = episodeDao.getPlayedFeedItems()

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