package com.hustcaster.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "updates")
data class Update(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("record_id") val id:Long=0,
    @ColumnInfo("episode_id") val episode: Long
)