package com.hustcaster.app.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PodcastAndEpisodes(
    @Embedded
    val podcast: Podcast,

    @Relation(parentColumn = "id", entityColumn = "podcast_id")
    val episodes: List<Episode>
)
