package com.hustcaster.app.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.PodcastAndEpisodes

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview
fun PodcastItem(
    podcastAndEpisodes: PodcastAndEpisodes = PodcastAndEpisodes(
        Podcast(
            rssUrl = "",
            title = "123",
            author = "abc"
        )
    ),
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = podcastAndEpisodes.podcast.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = podcastAndEpisodes.podcast.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = podcastAndEpisodes.podcast.author,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = podcastAndEpisodes.items.size.toString() + "单集",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}