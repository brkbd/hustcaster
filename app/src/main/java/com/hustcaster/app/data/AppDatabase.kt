package com.hustcaster.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hustcaster.app.utils.Converters

@Database(version = 1, entities = [Podcast::class, Episode::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): PodcastDao
    abstract fun feedItemDao(): EpisodeDao

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