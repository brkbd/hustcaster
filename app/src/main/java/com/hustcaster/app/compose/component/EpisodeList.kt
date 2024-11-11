package com.hustcaster.app.compose.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hustcaster.app.data.model.Episode
import java.util.Calendar

@Preview
@Composable
fun EpisodeList(
    modifier: Modifier = Modifier,
    episodes: List<Episode> = listOf(
        Episode(
            podcastId = 1,
            title = "111",
            pubDate = Calendar.getInstance(),
            duration = "01:50:50"
        ),
        Episode(
            podcastId = 1,
            title = "222",
            pubDate = Calendar.getInstance(),
            duration = "00:51:45"
        )
    ),
    imageUrl: String = ""
) {
    LazyColumn {
        items(episodes) { episode ->
            EpisodeItem(episode = episode, pictureUrl = imageUrl)
        }
    }
}
