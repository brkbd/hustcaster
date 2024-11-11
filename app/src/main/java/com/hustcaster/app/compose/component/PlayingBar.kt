package com.hustcaster.app.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayingBar(
    podcastTitle: String,
    podcastImageUrl: String,
    episodeTitle: String,
    isPlaying: Boolean,
    progress: Float,
    onButtonClick: () -> Unit,
    onBarClick: () -> Unit
) {
    Surface(
        onClick = onBarClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = podcastImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(start = 5.dp)
                )
                Column(modifier = Modifier.padding(start = 5.dp)) {
                    Text(
                        text = episodeTitle,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        text = podcastTitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Icon(
                    imageVector = if (isPlaying) Pause else Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(end = 50.dp)
                        .size(20.dp)
                        .clickable { onButtonClick() }
                )
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .height(3.dp)
                    .fillMaxWidth()
            )
        }
    }
}
