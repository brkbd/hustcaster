package com.hustcaster.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class PodcastAndEpisodes(
    @Embedded
    val podcast: Podcast,

    @Relation(parentColumn = "id", entityColumn = "feed_id")
    val items: List<Episode> = emptyList()
)
