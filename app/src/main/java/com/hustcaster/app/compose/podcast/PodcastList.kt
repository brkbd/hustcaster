package com.hustcaster.app.compose.podcast

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hustcaster.app.R
import com.hustcaster.app.data.model.Podcast

@Preview
@Composable
fun PodcastList(
    modifier: Modifier = Modifier,
    podcasts: List<Podcast> = listOf(
        Podcast(""),
        Podcast(""),
        Podcast("")
    )
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PodcastListTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onPodcastClick: () -> Unit = {}
) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.my_podcasts),
            style = MaterialTheme.typography.titleMedium,
        )
    })
}