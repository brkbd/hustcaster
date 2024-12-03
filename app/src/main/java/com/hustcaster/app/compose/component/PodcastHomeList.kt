package com.hustcaster.app.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.PodcastAndEpisodes

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PodcastHomeList(
    modifier: Modifier = Modifier,
    podcasts: List<PodcastAndEpisodes>,
    onMoreClick: () -> Unit,
    onPodcastClick: (PodcastAndEpisodes) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.my_podcasts),
                    style = MaterialTheme.typography.titleLarge
                )
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { onMoreClick() }
                ) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.more_podcasts),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            LazyRow {
                items(podcasts) { podcast ->
                    GlideImage(
                        model = podcast.podcast.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .clickable { onPodcastClick(podcast) },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

    }
}