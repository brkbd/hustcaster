package com.hustcaster.app.compose.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.hustcaster.app.compose.component.RecordCard
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.viewmodels.RecordListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordListScreen(
    onPlayClick: (Episode) -> Unit,
    onNavigationIconClick: () -> Unit,
    recordListViewModel: RecordListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val records = recordListViewModel.records.collectAsState()
    Scaffold(
        topBar = {
            RecordListTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationIconClick = onNavigationIconClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
                items(records.value) { record ->
                    RecordCard(
                        imageUrl = record.episode.imageUrl,
                        title = record.episode.title,
                        description = record.episode.description
                    ) {
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
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationIconClick: () -> Unit
) {
    CustomizedTopAppBar(
        title = stringResource(id = R.string.record),
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationIconClick = onNavigationIconClick,
        actionIcon = null,
        onActionIconClick = { },
        scrollBehavior = scrollBehavior
    )
}