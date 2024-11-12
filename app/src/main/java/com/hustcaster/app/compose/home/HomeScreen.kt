package com.hustcaster.app.compose.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.component.PodcastHomeList
import com.hustcaster.app.compose.component.RecordHomeList
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMoreRecordClick: () -> Unit = {},
    onMorePodcastClick: () -> Unit = {},
    onPlayRecordClick: (Episode) -> Unit = {},
    onPodcastClick: (Podcast) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            HomeTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                RecordHomeList(
                    onMoreClick = onMoreRecordClick,
                    onPlayClick = onPlayRecordClick
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                PodcastHomeList(
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