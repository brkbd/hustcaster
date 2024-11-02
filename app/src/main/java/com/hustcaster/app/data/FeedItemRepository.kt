package com.hustcaster.app.data

import javax.inject.Singleton

@Singleton
class FeedItemRepository(
    private val feedItemDao: FeedItemDao
) {
    suspend fun saveFeedItem(feedItem: FeedItem) {
        feedItemDao.insertItem(feedItem)
    }

    suspend fun removeFeedItem(feedItem: FeedItem) {
        feedItemDao.deleteItem(feedItem)
    }

    fun getFeedAndFeedItems() = feedItemDao.getFeedAndFeedItems()

    fun getDownloadedFeedItems() = feedItemDao.getDownloadedFeedItems()

    fun getPlayedFeedItems() = feedItemDao.getPlayedFeedItems()

    companion object {
        @Volatile
        private var instance: FeedItemRepository? = null

        fun getInstance(feedItemDao: FeedItemDao) =
            instance ?: synchronized(this) {
                instance ?: FeedItemRepository(feedItemDao).also { instance = it }
            }

    }
}