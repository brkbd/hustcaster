package com.hustcaster.app.data.repository

import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.dao.PodcastDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PodcastRepository @Inject constructor(
    private val podcastDao: PodcastDao
) {
    suspend fun savePodcast(podcast: Podcast) = podcastDao.insertPodcast(podcast)

    suspend fun removePodcast(podcast: Podcast) = podcastDao.deletePodcast(podcast)

    suspend fun updatePodcast(podcast: Podcast) = podcastDao.updatePodcast(podcast)

    fun getImageUrlByPodcastId(feedId: Long) = podcastDao.getPodcastPictureUrlById(feedId)

    fun getAllPodcastsFlow() = podcastDao.queryAllPodcastsFlow()

    fun getAllPodcasts()=podcastDao.getAllPodcasts()
    fun getAllPodcastsAndEpisodes()=podcastDao.queryAllPodcastsAndEpisode()

    fun getPodcastAndEpisodesById(id: Long) = podcastDao.getPodcastAndEpisodesById(id)

    fun getPodcastById(id: Long) = podcastDao.getPodcastById(id)

    fun getPodcastIdByRssUrl(url: String) = podcastDao.getPodcastIdByRssUrl(url)


    companion object {
        @Volatile
        private var instance: PodcastRepository? = null

        fun getInstance(podcastDao: PodcastDao) =
            instance ?: synchronized(this) {
                instance ?: PodcastRepository(podcastDao).also { instance = it }
            }
    }
}