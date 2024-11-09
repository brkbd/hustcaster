package com.hustcaster.app.data

import javax.inject.Singleton

@Singleton
class PodcastRepository(
    private val podcastDao: PodcastDao
) {
    suspend fun saveFeed(podcast: Podcast) = podcastDao.insertFeed(podcast)

    suspend fun removeFeed(podcast: Podcast) = podcastDao.deleteFeed(podcast)

    suspend fun updateFeed(podcast: Podcast) = podcastDao.updateFeed(podcast)

    fun getImageUrlByFeedId(feedId: Long) = podcastDao.queryFeedPictureUrlById(feedId)

    fun getAllFeeds() = podcastDao.queryAllFeeds()

    companion object {
        @Volatile
        private var instance: PodcastRepository? = null

        fun getInstance(podcastDao: PodcastDao) =
            instance ?: synchronized(this) {
                instance ?: PodcastRepository(podcastDao).also { instance = it }
            }
    }
}