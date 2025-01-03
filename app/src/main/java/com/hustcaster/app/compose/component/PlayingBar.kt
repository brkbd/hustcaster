package com.hustcaster.app.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayingBar(
    podcastTitle: String,
    episodeImageUrl: String,
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
            .height(80.dp),
        color = Color(0x0f00ffff)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = episodeImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(65.dp)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(2.dp))
                )
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .padding(top = 5.dp)
                        .width(210.dp)//weight
                ) {
                    Text(
                        text = episodeTitle,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    Text(
                        text = podcastTitle,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = if (isPlaying) Pause else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .padding(end = 30.dp)
                            .size(28.dp)
                            .clickable { onButtonClick() }
                    )
                }

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
