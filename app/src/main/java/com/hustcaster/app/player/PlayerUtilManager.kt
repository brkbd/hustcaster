package com.hustcaster.app.player

import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.data.repository.RecordRepository
import com.hustcaster.app.utils.MediaUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerUtilManager @Inject constructor(
    podcastRepository: PodcastRepository,
    episodeRepository: EpisodeRepository,
    recordRepository: RecordRepository
) {
    init {
        MediaUtil.setEpisodeRepository(episodeRepository)
        ExoPlayerHolder.setRepository(recordRepository, episodeRepository, podcastRepository)
    }
}