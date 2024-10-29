package com.hustcaster.app.data

data class Feed(
    var title: String,
    var link: String,
    var description: String,
    var language: String,
    var author: String,
    var imageUrl: String,
    var items: List<FeedItem>
)