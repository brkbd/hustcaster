package com.hustcaster.app.di

import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import com.hustcaster.app.player.PlayerUtilManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlayerModule {
    @Provides
    @Singleton
    fun providePlayerUtilManager(
        podcastRepository: PodcastRepository,
        episodeRepository: EpisodeRepository,
        recordRepository: RecordRepository
    ): PlayerUtilManager {
        return PlayerUtilManager(podcastRepository, episodeRepository, recordRepository)
    }
}