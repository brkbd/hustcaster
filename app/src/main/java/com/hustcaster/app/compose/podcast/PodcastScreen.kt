package com.hustcaster.app.compose.podcast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.component.EpisodeList
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.viewmodels.PodcastViewModel

@Composable
fun PodcastScreen(
    podcastAndEpisodes: PodcastAndEpisodes,
    onInfoClick: () -> Unit,
    onBackClick: () -> Unit,
    onPlayAllClick: () -> Unit,
    onEpisodeClick: (Episode) -> Unit
) {
    val viewModel = PodcastViewModel(podcastAndEpisodes)
    Scaffold(
        topBar = {
            PodcastTopAppBar(onBackClick = onBackClick)
        },
        bottomBar = {
            PlayingBar(
                podcastTitle = viewModel.title,
                podcastImageUrl = viewModel.imageUrl,
                episodeTitle = viewModel.episodes[0].title,
                isPlaying = true,
                progress = 5f,
                onButtonClick = {},
                onBarClick = {}
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                PodcastInfoBox(
                    imageUrl = viewModel.imageUrl,
                    title = viewModel.title,
                    author = viewModel.author,
                    onInfoClick = onInfoClick,
                    description = viewModel.description
                )
                PlayAllBar(onPlayAllClick = onPlayAllClick)
                EpisodeList(
                    episodes = viewModel.episodes,
                    imageUrl = viewModel.imageUrl,
                    onEpisodeClick = onEpisodeClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    CustomizedTopAppBar(
        title = stringResource(id = R.string.podcast),
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationIconClick = onBackClick,
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PodcastInfoBox(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    title: String = "6 minutes English",
    author: String = "abc",
    description: String = "111111111111111111111111111111111111111111111111111111111111",
    onInfoClick: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .padding(horizontal = 10.dp)
        ) {
            GlideImage(
                model = imageUrl, contentDescription = null,
                modifier = Modifier
                    .size(125.dp)

            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Text(
                    text = "作者：$author",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { onInfoClick() }
                )
            }
        }
    }
}


@Composable
fun PlayAllBar(
    modifier: Modifier = Modifier,
    onPlayAllClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                modifier = Modifier.clickable { onPlayAllClick() })
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "播放全部", style = MaterialTheme.typography.titleSmall)
        }
    }
}

