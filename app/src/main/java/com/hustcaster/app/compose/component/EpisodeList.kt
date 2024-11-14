package com.hustcaster.app.compose.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hustcaster.app.data.model.Episode
import java.util.Calendar

@Composable
fun EpisodeList(
    modifier: Modifier = Modifier,
    episodes: List<Episode>,
    imageUrl: String,
    onEpisodeClick: (Episode) -> Unit
) {
    LazyColumn {
        items(episodes) { episode ->
            EpisodeItem(
                episode = episode,
                pictureUrl = imageUrl,
                onEpisodeClick = { onEpisodeClick(episode) })
        }
    }
}
