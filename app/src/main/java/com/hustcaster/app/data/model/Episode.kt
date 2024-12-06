package com.hustcaster.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(
    tableName = "episodes",
    foreignKeys = [
        ForeignKey(entity = Podcast::class, parentColumns = ["id"], childColumns = ["podcast_id"])
    ],
    indices = [Index("podcast_id")]
)
data class Episode(
    @ColumnInfo("podcast_id") var podcastId: Long = 0,
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("description") var description: String = "",
    @ColumnInfo("author") var author: String = "",
    @ColumnInfo("image_url") var imageUrl: String = "",
    @ColumnInfo("pub_date") var pubDate: Calendar? = null,
    @ColumnInfo("duration") var duration: Long = 0,
    @ColumnInfo("audio_url") var audioUrl: String = "",
    @ColumnInfo("is_downloaded") var isDownloaded: Boolean = false,
    @ColumnInfo("download_url") var downloadUrl: String = "",
    @ColumnInfo("is_updated") var isUpdated: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("episode_id")
    val episodeId: Long = 0
)