package com.hustcaster.app.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.common.NavigationBarImpl
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.compose.component.PodcastHomeList
import com.hustcaster.app.compose.component.RecordHomeList
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMoreRecordClick: () -> Unit,
    onMorePodcastClick: () -> Unit,
    onPlayRecordClick: (Episode) -> Unit,
    onPodcastClick: (PodcastAndEpisodes) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val records = homeViewModel.records.collectAsState()
    val podcasts = homeViewModel.podcasts.collectAsState()
    Scaffold(
        topBar = {
            HomeTopAppBar(scrollBehavior = scrollBehavior)
        },
        bottomBar = {
            Column {
                PlayingBar(
                    podcastTitle = "",
                    podcastImageUrl = "",
                    episodeTitle = "",
                    isPlaying = true,
                    progress = 0.5f,
                    onButtonClick = { /*TODO*/ }) {

                }
                NavigationBarImpl()
            }

        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                RecordHomeList(
                    records = records.value,
                    onMoreClick = onMoreRecordClick,
                    onPlayClick = onPlayRecordClick
                )
                Spacer(modifier = Modifier.height(10.dp))
                PodcastHomeList(
                    podcasts = podcasts.value,
                    onMoreClick = onMorePodcastClick,
                    onPodcastClick = onPodcastClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.home),
        navigationIcon = null,
        onNavigationIconClick = { },
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}