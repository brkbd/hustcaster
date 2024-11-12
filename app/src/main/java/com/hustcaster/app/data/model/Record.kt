package com.hustcaster.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id") val id: Long = 0,
    @ColumnInfo(name = "episode_id") val episodeId: Long
)