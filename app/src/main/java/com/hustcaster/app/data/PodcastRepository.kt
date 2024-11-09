package com.hustcaster.app.data

import javax.inject.Singleton

@Singleton
class PodcastRepository(
    private val podcastDao: PodcastDao
) {
    suspend fun savePodcast(podcast: Podcast) = podcastDao.insertPodcast(podcast)

    suspend fun removePodcast(podcast: Podcast) = podcastDao.deletePodcast(podcast)

    suspend fun updatePodcast(podcast: Podcast) = podcastDao.updatePodcast(podcast)

    fun getImageUrlByPodcastId(feedId: Long) = podcastDao.getPodcastPictureUrlById(feedId)

    fun getAllPodcasts() = podcastDao.queryAllPodcasts()

    companion object {
        @Volatile
        private var instance: PodcastRepository? = null

        fun getInstance(podcastDao: PodcastDao) =
            instance ?: synchronized(this) {
                instance ?: PodcastRepository(podcastDao).also { instance = it }
            }
    }
}