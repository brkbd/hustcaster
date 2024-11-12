package com.hustcaster.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hustcaster.app.data.dao.EpisodeDao
import com.hustcaster.app.data.dao.PodcastDao
import com.hustcaster.app.data.dao.RecordDao
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.Record
import com.hustcaster.app.utils.Converters

@Database(version = 1, entities = [Podcast::class, Episode::class, Record::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build().apply {
                instance = this
            }
        }
    }
}