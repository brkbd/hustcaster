package com.hustcaster.app.data

data class FeedState(
    val feed: Feed = Feed(),
    var currentFeedItem: FeedItem? = null
) {
    fun saveFeed() {

    }
}