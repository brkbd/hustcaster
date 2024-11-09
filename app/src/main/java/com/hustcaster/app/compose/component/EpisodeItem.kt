package com.hustcaster.app.compose.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.data.Episode
import com.hustcaster.app.ui.theme.HustcasterTheme
import com.hustcaster.app.utils.dateFormat
import java.util.Calendar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EpisodeItem(
    episode: Episode,
    pictureUrl: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.episode_surface_rounded_corner_shape)),
        shadowElevation = dimensionResource(id = R.dimen.episode_surface_shadow_elevation),
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.episode_padding_horizontal))
            .height(80.dp),
        color = Color.White
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
                Text(text = episode.title, style = MaterialTheme.typography.titleMedium)
                Row(
                    modifier = modifier.padding(
                        vertical = dimensionResource(
                            id = R.dimen.title_additional_infos_padding
                        )
                    )
                ) {
                    Text(
                        text = dateFormat.format(episode.pubDate!!.time),
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
                    Text(text = episode.duration, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }

}

@Preview
@Composable
fun EpisodeItemPreview() {
    HustcasterTheme {
        EpisodeItem(
            episode = Episode(
                title = "123",
                pubDate = Calendar.getInstance(),
                podcastId = 1,
                duration = "01:50:30"
            ),
            pictureUrl = "https://static.libsyn.com/p/assets/c/c/e/a/ccea20418e7aceed27a2322813b393ee/6M_Pod_New_Boink_Vertical_Logo_2024.png"
        )
    }
}