package com.hustcaster.app.compose.listen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowForward
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hustcaster.app.compose.common.CustomizedTopAppBar
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ListenScreen(
    imageUrl:String,
    onCoverClick:()->Unit,
    episodeTitle:String,
    podcastTitle:String,
    currentProgress:String,
    duration:String,
    isBuffering:Boolean,
    isPlaying:Boolean,
    onValueChange:(Float)->Unit,
    onValueChangeFinished:()->Unit,
    setPlayBackSpeed:(Float)->Unit,
    replay:()->Unit,
    playOrPause:()->Unit,
    forward:()->Unit
){
    val scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        topBar = {
            ListenTopAppBar(scrollBehavior = scrollBehavior)
        },
    ) {innerPadding ->  
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    model = imageUrl, 
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f, true)
                        .clip(MaterialTheme.shapes.large)
                        .clickable { onCoverClick() }
                )
            }
            Column(
                modifier = Modifier
                    .weight(3f)
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
                Slider(
                    value = 0f, 
                    onValueChange = onValueChange,
                    modifier = Modifier.padding(top = 12.dp),
                    onValueChangeFinished = onValueChangeFinished
                )
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = currentProgress,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = duration,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                val speedList= listOf(1.0f,1.2f,1.5f,0.8f)
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
                        //listenViewModel.setPlayBackSpeed(speedList[speedIndex])
                    }) {
                        Text(
                            text = ("${speedList[speedIndex]}x"),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { /*listenViewModel.replay()*/ },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    FilledIconButton(
                        onClick = { /*listenViewModel.playOrPause()*/ },
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
                                imageVector = if (isPlaying || isBuffering) Icons.Outlined.Close else Icons.Filled.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = { /*listenViewModel.forward()*/ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription =null )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListenTopAppBar(scrollBehavior: TopAppBarScrollBehavior){
    CustomizedTopAppBar(
        title = "",
        navigationIcon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        onNavigationIconClick = { /*TODO*/ },
        actionIcon = null,
        onActionIconClick = { /*TODO*/ },
        scrollBehavior =scrollBehavior
    )
}