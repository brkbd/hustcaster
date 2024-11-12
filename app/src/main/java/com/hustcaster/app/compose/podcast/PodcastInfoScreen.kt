package com.hustcaster.app.compose.podcast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.compose.component.CustomizedTopAppBar
import com.hustcaster.app.utils.dateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Preview
@Composable
fun PodcastInfoScreen(
    title: String = "6 minutes English",
    imageUrl: String = "",
    author: String = "abc",
    pubDate: Calendar = Calendar.getInstance(),
    description: String = "11111111111111111111111111111111222222222222222222222222222233333333333333333333333333333333333",
    onCloseClick: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            PodcastTopAppBar(
                onCloseClick = { onCloseClick() },
                scrollBehavior = scrollBehavior
            )
        }

    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        GlideImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(150.dp)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = author,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = "发布日期：${dateFormat.format(pubDate.time)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = "播客介绍：",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = Int.MAX_VALUE
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(text = description)
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastTopAppBar(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CustomizedTopAppBar(
        title = "",
        navigationIcon = null,
        onNavigationIconClick = { },
        actionIcon = Icons.Filled.Close,
        onActionIconClick = onCloseClick,
        scrollBehavior = scrollBehavior
    )
}
