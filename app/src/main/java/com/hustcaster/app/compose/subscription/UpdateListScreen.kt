package com.hustcaster.app.compose.subscription

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.common.NavigationBarImpl
import com.hustcaster.app.compose.component.EpisodeItem
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.player.ExoPlayerHolder
import com.hustcaster.app.viewmodels.UpdateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateListScreen(
    viewModel: UpdateViewModel = hiltViewModel(),
    navigateToListenScreen: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val playerState = ExoPlayerHolder.playerStateFlow.collectAsState()
    val episodes = viewModel.updateEpisodes.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            UpdateTopAppBar(scrollBehavior = scrollBehavior,
                onDeleteClick = {
                    coroutineScope.launch {
                        viewModel.onDeleteClick()
                    }
                })
        },
        bottomBar = {
            Column {
                playerState.value.run {
                    PlayingBar(
                        podcastTitle = currentPodcast.title,
                        episodeImageUrl = currentEpisode.imageUrl,
                        episodeTitle = currentEpisode.title,
                        isPlaying = isPlaying,
                        progress = currentProgress,
                        onButtonClick = { viewModel.playOrPause() },
                        onBarClick = navigateToListenScreen
                    )
                }

                NavigationBarImpl()
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(episodes.value) { episode ->
                    EpisodeItem(episode = episode, pictureUrl = episode.imageUrl) {
                        coroutineScope.launch {
                            viewModel.onEpisodeClick(episode.episodeId)
                            navigateToListenScreen()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onDeleteClick:()->Unit={}
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.inbox),
        navigationIcon = null,
        onNavigationIconClick = { },
        actionIcon = Icons.Filled.Delete,
        onActionIconClick = onDeleteClick,
        scrollBehavior = scrollBehavior
    )
}
