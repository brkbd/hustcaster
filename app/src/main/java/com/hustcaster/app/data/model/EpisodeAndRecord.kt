package com.hustcaster.app.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class EpisodeAndRecord (
    @Embedded val record: Record,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "episode_id"
    )
    val episode: Episode
)