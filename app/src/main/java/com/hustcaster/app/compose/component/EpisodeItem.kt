package com.hustcaster.app.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.ui.theme.HustcasterTheme
import com.hustcaster.app.utils.convertLongToDurationString
import com.hustcaster.app.utils.dateFormat
import java.util.Calendar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EpisodeItem(
    episode: Episode,
    pictureUrl: String,
    modifier: Modifier = Modifier,
    onEpisodeClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(80.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            GlideImage(
                model = pictureUrl,
                contentDescription = episode.description,
                modifier = modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.episode_padding_between_image_text))
            ) {
                Text(
                    text = episode.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = modifier.padding(
                        vertical = dimensionResource(
                            id = R.dimen.title_additional_infos_padding
                        )
                    )
                ) {
                    Text(
                        text = episode.pubDate?.time.let { if(it==null) "暂无日期" else dateFormat.format(it) },
                        style = MaterialTheme.typography.labelSmall
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = stringResource(
                            id = R.string.duration
                        ),
                        modifier = modifier.padding(
                            vertical = dimensionResource(
                                id = R.dimen.duration_icon_padding_vertical
                            ),
                            horizontal = dimensionResource(
                                id = R.dimen.duration_icon_padding_horizontal
                            )
                        )
                    )
                    Text(text = convertLongToDurationString(episode.duration) , style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }

}
