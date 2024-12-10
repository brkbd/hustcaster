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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.component.EpisodeList
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.player.ExoPlayerHolder
import com.hustcaster.app.viewmodels.PodcastViewModel
import kotlinx.coroutines.launch

@Composable
fun PodcastScreen(
    viewModel: PodcastViewModel = hiltViewModel(),
    onInfoClick: () -> Unit,
    onBackClick: () -> Unit,
    onPlayAllClick: () -> Unit,
    navigateToListenPage: () -> Unit
) {
    val podcastAndEpisodes by viewModel.podcastAndEpisodes.observeAsState()
    val podcast = podcastAndEpisodes?.podcast
    val episodes = podcastAndEpisodes?.episodes
    val playerState = ExoPlayerHolder.playerStateFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PodcastTopAppBar(onBackClick = onBackClick)
        },
        bottomBar = {
            playerState.value.run {
                PlayingBar(
                    podcastTitle = currentPodcast.title,
                    podcastImageUrl = currentEpisode.imageUrl,
                    episodeTitle = currentEpisode.title,
                    isPlaying = isPlaying,
                    progress = currentProgress,
                    onButtonClick = { viewModel.playOrPause() },
                    onBarClick = navigateToListenPage
                )
            }

        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column {
                PodcastInfoBox(
                    imageUrl = podcast?.imageUrl ?: "",
                    title = podcast?.title ?: "",
                    author = podcast?.title ?: "",
                    onInfoClick = onInfoClick,
                    description = podcast?.description?.trim() ?: "暂无介绍"
                )
                PlayAllBar(onPlayAllClick = onPlayAllClick)
                EpisodeList(
                    episodes = episodes ?: emptyList(),
                    imageUrl = podcast?.imageUrl ?: "",
                    onEpisodeClick = { episodeId ->
                        coroutineScope.launch {
                            viewModel.onEpisodeClick(episodeId)
                        }
                        //navigateToListenPage()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastTopAppBar(
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
    imageUrl: String,
    title: String,
    author: String,
    description: String,
    onInfoClick: () -> Unit
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
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(vertical = 15.dp))
                Text(
                    text = "作者：$author",
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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

