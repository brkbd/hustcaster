package com.hustcaster.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class FeedAndFeedItems(
    @Embedded
    val feed: Feed,

    @Relation(parentColumn = "id", entityColumn = "feed_id")
    val items: List<FeedItem> = emptyList()
)
