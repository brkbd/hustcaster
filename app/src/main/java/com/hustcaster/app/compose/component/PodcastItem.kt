package com.hustcaster.app.compose.component

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hustcaster.app.data.PodcastAndEpisodes

@Composable
fun PodcastItem(
    podcastAndEpisodes: PodcastAndEpisodes,
    modifier: Modifier = Modifier,
    onClick: (PodcastAndEpisodes) -> Unit
) {
    Card(onClick = { onClick(podcastAndEpisodes) }, modifier = modifier) {

    }
}