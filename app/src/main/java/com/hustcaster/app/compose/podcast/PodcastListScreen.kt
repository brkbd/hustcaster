package com.hustcaster.app.compose.podcast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hustcaster.app.R
import com.hustcaster.app.compose.component.CustomizedTopAppBar
import com.hustcaster.app.compose.component.PodcastItem
import com.hustcaster.app.data.model.PodcastAndEpisodes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastListScreen(
    modifier: Modifier = Modifier,
    podcasts: List<PodcastAndEpisodes>,
    onBackClick: () -> Unit,
    onPodcastClick: (PodcastAndEpisodes) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            PodcastListTopAppBar(
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.card_side_margin))
            ) {
                items(podcasts) { podcast ->
                    PodcastItem(podcastAndEpisodes = podcast) {
                        onPodcastClick(podcast)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastListTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.my_podcasts),
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationIconClick = onBackClick,
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}