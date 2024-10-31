package com.hustcaster.app.data

data class FeedState(
    val feed: Feed = Feed(),
    val currentFeedItem: FeedItem? = null
) {
    fun saveFeed() {

    }
}