package com.hustcaster.app.viewmodels

import com.hustcaster.app.data.model.PodcastAndEpisodes

class PodcastViewModel(podcastAndEpisodes: PodcastAndEpisodes) {
    private val podcast = podcastAndEpisodes.podcast
    private val episodeList = podcastAndEpisodes.episodes

    val title
        get() = podcast.title
    val author
        get() = podcast.author
    val imageUrl
        get() = podcast.imageUrl
    val description
        get() = podcast.description
    val episodes
        get() = episodeList
    val pubDate
        get() = podcast.pubDate
}