package com.hustcaster.app.data

class FeedItemRepository(
    private val feedItemDao: FeedItemDao
) {
    suspend fun saveFeedItem(feedItem: FeedItem) {
        feedItemDao.insertItem(feedItem)
    }

    suspend fun removeFeedItem(feedItem: FeedItem) {
        feedItemDao.deleteItem(feedItem)
    }

//    fun isDownloaded(feedItem: FeedItem)
//    =feedItemDao.isDownloaded(feedItem.itemId)

    fun getFeedAndFeedItems() = feedItemDao.getFeedAndFeedItems()

    companion object {
        @Volatile
        private var instance: FeedItemRepository? = null

        fun getInstance(feedItemDao: FeedItemDao) =
            instance ?: synchronized(this) {
                instance ?: FeedItemRepository(feedItemDao).also { instance = it }
            }

    }
}