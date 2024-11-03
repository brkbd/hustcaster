package com.hustcaster.app.data

import javax.inject.Singleton

@Singleton
class FeedRepository(
    private val feedDao: FeedDao
) {
    suspend fun saveFeed(feed: Feed) = feedDao.insertFeed(feed)

    suspend fun removeFeed(feed: Feed) = feedDao.deleteFeed(feed)

    fun getAllFeeds() = feedDao.queryAllFeeds()

    companion object {
        @Volatile
        private var instance: FeedRepository? = null

        fun getInstance(feedDao: FeedDao) =
            instance ?: synchronized(this) {
                instance ?: FeedRepository(feedDao).let { instance = it }
            }
    }
}