package com.hustcaster.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(
    tableName = "feedItems",
    foreignKeys = [
        ForeignKey(entity = Feed::class, parentColumns = ["id"], childColumns = ["feed_id"])
    ],
    indices = [Index("feed_id")]
)
data class FeedItem(
    @ColumnInfo("feed_id") val feedId: Long,
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("description") var description: String = "",
    @ColumnInfo("subtitle") val subtitle: String = "",
    @ColumnInfo("pub_date") var pubDate: Calendar? = null,
    @ColumnInfo("duration") val duration: String = "",
    @ColumnInfo("audio_url") var audioUrl: String = "",
    @ColumnInfo("is_downloaded") var isDownloaded: Boolean = false,
    @ColumnInfo("is_played") var isPlayed: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("item_id")
    val itemId: Long = 0
)