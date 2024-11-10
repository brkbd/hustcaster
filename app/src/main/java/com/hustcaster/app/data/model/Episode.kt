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
    @ColumnInfo("podcast_id") val podcastId: Long,
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("description") var description: String = "",
    @ColumnInfo("pub_date") var pubDate: Calendar? = null,
    @ColumnInfo("duration") var duration: String = "",
    @ColumnInfo("audio_url") var audioUrl: String = "",
    @ColumnInfo("is_downloaded") var isDownloaded: Boolean = false,
    @ColumnInfo("download_url") var downloadUrl: String = "",
    @ColumnInfo("is_played") var isPlayed: Boolean = false,
    @ColumnInfo("is_updated") var isUpdated: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("episode_id")
    val episodeId: Long = 0
)