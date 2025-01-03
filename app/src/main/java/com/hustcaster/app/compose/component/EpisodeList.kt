package com.hustcaster.app.compose.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.hustcaster.app.data.model.Episode

@Composable
fun EpisodeList(
    episodes: List<Episode>,
    onEpisodeClick: (Long) -> Unit
) {
    LazyColumn {
        items(episodes) { episode ->
            EpisodeItem(
                episode = episode,
                pictureUrl = episode.imageUrl,
                onEpisodeClick = { onEpisodeClick(episode.episodeId) })
        }
    }
}
