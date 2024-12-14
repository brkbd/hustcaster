package com.hustcaster.app.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class EpisodeAndUpdate (
    @Embedded val update:Update,
    @Relation(
        parentColumn = "episode_id",
        entityColumn = "episode_id"
    )
    val episode:Episode
)
