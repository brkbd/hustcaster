package com.hustcaster.app.di

import android.content.Context
import com.hustcaster.app.data.AppDatabase
import com.hustcaster.app.data.dao.EpisodeDao
import com.hustcaster.app.data.dao.PodcastDao
import com.hustcaster.app.data.dao.RecordDao
import com.hustcaster.app.data.dao.UpdateDao
import com.hustcaster.app.data.repository.PodcastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase{
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePodcastDao(appDatabase: AppDatabase):PodcastDao{
        return appDatabase.podcastDao()
    }

    @Provides
    fun provideEpisodeDao(appDatabase: AppDatabase):EpisodeDao{
        return appDatabase.episodeDao()
    }

    @Provides
    fun provideRecordDao(appDatabase: AppDatabase):RecordDao{
        return appDatabase.recordDao()
    }

    @Provides
    fun provideUpdateDao(appDatabase: AppDatabase):UpdateDao{
        return appDatabase.updateDao()
    }
}