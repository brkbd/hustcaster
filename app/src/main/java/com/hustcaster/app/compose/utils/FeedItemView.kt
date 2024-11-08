package com.hustcaster.app.compose.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.data.FeedItem
import com.hustcaster.app.ui.theme.HustcasterTheme
import com.hustcaster.app.utils.dateFormat
import java.util.Calendar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedItemView(
    feedItem: FeedItem,
    pictureUrl: String,
    modifier: Modifier = Modifier
) {
    Row {
        GlideImage(
            model = pictureUrl,
            contentDescription = feedItem.description,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
        Column {
            Text(text = feedItem.title)
            Row {
                Text(text = dateFormat.format(feedItem.pubDate))
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = stringResource(
                        id = R.string.duration
                    )
                )
                Text(text = feedItem.duration)
            }
        }
    }
}

@Preview
@Composable
fun FeedItemViewPreview() {
    HustcasterTheme {
        FeedItemView(
            feedItem = FeedItem(
                title = "123",
                pubDate = Calendar.getInstance(),
                feedId = 1
            ),
            pictureUrl = "https://static.libsyn.com/p/assets/c/c/e/a/ccea20418e7aceed27a2322813b393ee/6M_Pod_New_Boink_Vertical_Logo_2024.png"
        )
    }
}