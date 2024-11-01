package com.hustcaster.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feed(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var pubDate: String = "",
    var author: String = "",
    var imageUrl: String = "",
    val items: MutableList<FeedItem> = mutableListOf()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}