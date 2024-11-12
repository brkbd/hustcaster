package com.hustcaster.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val episodeId: Long
)