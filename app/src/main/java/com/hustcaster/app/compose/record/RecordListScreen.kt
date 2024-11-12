package com.hustcaster.app.compose.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.component.RecordCard
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Record

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RecordListScreen(
    modifier: Modifier = Modifier,
    records: List<EpisodeAndRecord> = listOf(
        EpisodeAndRecord(
            Record(episodeId = 1),
            Episode(podcastId = 1, title = "111", description = "111111111111111111111111")
        ),
        EpisodeAndRecord(
            Record(episodeId = 2),
            Episode(podcastId = 1, title = "222", description = "22222222222222222222222222222222")
        ),
        EpisodeAndRecord(
            Record(episodeId = 3),
            Episode(podcastId = 1, title = "333", description = "333")
        )
    ),
    onPlayClick: (Episode) -> Unit = {}

) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            RecordListTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                items(records) { record ->
                    RecordCard {
                        onPlayClick(record.episode)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordListTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.record),
        navigationIcon = null,
        onNavigationIconClick = { },
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}