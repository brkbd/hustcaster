package com.hustcaster.app.compose.subscription

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hustcaster.app.R
import com.hustcaster.app.compose.component.CustomizedTopAppBar
import com.hustcaster.app.compose.component.EpisodeItem
import com.hustcaster.app.data.model.Episode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEpisodeList(
    modifier: Modifier = Modifier,
    episodes: List<Episode>,
    onEpisodeClick: (Episode) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            UpdateEpisodeTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(episodes) { episode ->
                    EpisodeItem(episode = episode, pictureUrl = "") {
                        onEpisodeClick(episode)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEpisodeTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.inbox),
        navigationIcon = null,
        onNavigationIconClick = { },
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}