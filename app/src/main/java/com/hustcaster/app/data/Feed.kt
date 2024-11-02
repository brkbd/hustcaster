package com.hustcaster.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "feeds",
    indices = [Index("id")]
)
data class Feed(
    //record the rss url to check for updates
    @ColumnInfo("rss_url") val rssUrl: String,
    @ColumnInfo("title") var title: String = "",
    @ColumnInfo("link") var link: String = "",
    @ColumnInfo("description") var description: String = "",
    @ColumnInfo("pub_date") var pubDate: String = "",
    @ColumnInfo("author") val author: String = "",
    @ColumnInfo("image_url") val imageUrl: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Long = 0
}