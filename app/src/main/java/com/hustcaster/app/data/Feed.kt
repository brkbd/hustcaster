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
    @ColumnInfo("title") val title: String = "",
    @ColumnInfo("link") val link: String = "",
    @ColumnInfo("description") val description: String = "",
    @ColumnInfo("pub_date") val pubDate: String = "",
    @ColumnInfo("author") val author: String = "",
    @ColumnInfo("image_url") val imageUrl: String = "",
    val items: MutableList<FeedItem> = mutableListOf()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Long = 0
}