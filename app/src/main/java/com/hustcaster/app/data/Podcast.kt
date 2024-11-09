package com.hustcaster.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(
    tableName = "podcasts",
    indices = [Index("id")]
)
data class Podcast(
    //record the rss url to check for updates
    @ColumnInfo("rss_url") val rssUrl: String,
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("link") var link: String = "",
    @ColumnInfo("description") var description: String = "",
    @ColumnInfo("pub_date") var pubDate: Calendar? = null,
    @ColumnInfo("author") var author: String = "",
    @ColumnInfo("image_url") var imageUrl: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Long = 0
}