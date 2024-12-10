package com.hustcaster.app.compose.listen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import com.hustcaster.app.compose.component.Pause
import com.hustcaster.app.player.ExoPlayerHolder
import com.hustcaster.app.utils.convertLongToDurationString
import com.hustcaster.app.utils.dateFormat
import com.hustcaster.app.viewmodels.ListenViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListenScreen(
    onDismiss: () -> Unit,
    listenViewModel: ListenViewModel = hiltViewModel(),
) {
    val playerState = ExoPlayerHolder.playerStateFlow.collectAsState()
    val viewState = playerState.value

    var showingEpisodeInfo by remember { mutableStateOf(false) }
    var isDragging by remember { mutableStateOf(false) }
    var slider by remember(isDragging) {
        isDragging = false
        mutableFloatStateOf(viewState.currentProgress)
    }
    val sliderPosition = remember(viewState.currentProgress) {
        derivedStateOf {
            if (isDragging) slider else viewState.currentProgress
        }
    }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        topBar = {
            ListenTopAppBar(
                scrollBehavior = scrollBehavior,
                onDismiss = onDismiss
            )
        },
    ) { innerPadding ->
        viewState.run {
            Column(modifier = Modifier.padding(innerPadding)) {
                Box(
                    modifier = Modifier
                        .weight(6f)
                        .clickable { showingEpisodeInfo = !showingEpisodeInfo }) {
                    if (showingEpisodeInfo) EpisodeInfoPage(
                        episodeTitle = currentEpisode.title,
                        podcastTitle = currentPodcast.title,
                        author = currentEpisode.title,
                        description = currentEpisode.description,
                        pubDate = currentEpisode.pubDate
                    )
                    else CoverPage(
                        imageUrl = currentEpisode.imageUrl,
                        episodeTitle = currentEpisode.title,
                        podcastTitle = currentPodcast.title
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Slider(
                        value = sliderPosition.value,
                        onValueChange = {
                            isDragging = true
                            slider = it
                        },
                        modifier = Modifier.padding(top = 12.dp),
                        onValueChangeFinished = {
                            coroutineScope.launch {
                                listenViewModel.seekToProgress(slider)
                            }
                        }
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = convertLongToDurationString(
                                (currentEpisode.duration * currentProgress).toLong()
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        Text(
                            text = convertLongToDurationString(currentEpisode.duration),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                    val speedList = listOf(1.0f, 1.2f, 1.5f, 0.8f)
                    var speedIndex by rememberSaveable {
                        mutableIntStateOf(0)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = {
                            if (speedIndex == 3) speedIndex = 0 else speedIndex += 1
                            listenViewModel.setPlayBackSpeed(speedList[speedIndex])
                        }) {
                            Text(
                                text = ("${speedList[speedIndex]}x"),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { listenViewModel.replay() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        FilledIconButton(
                            onClick = { listenViewModel.playOrPause() },
                            modifier = Modifier.size(54.dp)
                        ) {
                            AnimatedVisibility(
                                visible = isBuffering,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.surface
                                )
                            }
                            AnimatedVisibility(
                                visible = !isBuffering,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Icon(
                                    imageVector = if (isPlaying || isBuffering) Pause else Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                        IconButton(
                            onClick = { listenViewModel.forward() },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.forward),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoverPage(
    imageUrl: String,
    episodeTitle: String,
    podcastTitle: String
) {
    Column {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .clip(MaterialTheme.shapes.large)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = episodeTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = podcastTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .padding(12.dp, 6.dp)
                )
            }

        }
    }
}

@Composable
fun EpisodeInfoPage(
    episodeTitle: String,
    podcastTitle: String,
    author: String,
    description: String,
    pubDate: Calendar?
) {
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Text(text = "本集标题： $episodeTitle")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "所属博客： $podcastTitle")
                Text(text = "作者： $author")
                Text(text = "发布日期： ${dateFormat.format(pubDate?.time ?: "暂无信息")}")
                Text(text = "节目信息：$description")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onDismiss: () -> Unit
) {
    CustomizedTopAppBar(
        title = "",
        navigationIcon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        onNavigationIconClick = onDismiss,
        actionIcon = null,
        onActionIconClick = { /*TODO*/ },
        scrollBehavior = scrollBehavior
    )
}