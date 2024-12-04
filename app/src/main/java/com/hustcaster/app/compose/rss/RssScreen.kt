package com.hustcaster.app.compose.rss

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.NavigationBarImpl
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.viewmodels.RssScreenEvent
import com.hustcaster.app.viewmodels.RssViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RssScreen(
    rssViewModel: RssViewModel = hiltViewModel()
) {
    val snackbarHostState= remember {
        SnackbarHostState()
    }
    val currentValue = rssViewModel.rssUrlInput.collectAsState()
    val sharedFlow=rssViewModel.sharedFlow
    val isImporting by rssViewModel.isImporting.collectAsState()

    LaunchedEffect(sharedFlow) {
        sharedFlow.collect{
            when(it){
                RssScreenEvent.PODCAST_ALREADY_EXISTS -> {
                    snackbarHostState.showSnackbar("Podcast already exists!")
                }
                RssScreenEvent.IS_GETTING_PODCAST -> {
                    snackbarHostState.showSnackbar("Start fetching podcast, please wait...")
                }
                RssScreenEvent.FINISH_GETTING_PODCAST -> {
                    snackbarHostState.showSnackbar("Finish! Please go to home screen to check it out!")
                }

                RssScreenEvent.FAILED_PARSING_DATA -> {
                    snackbarHostState.showSnackbar("Something is going wrong when parsing data...")
                }
            }
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .padding(top = 180.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.create_podcast_world),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = currentValue.value,
                    onValueChange = rssViewModel::onValueChange,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                rssViewModel.onImportClick()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)){
                    if(isImporting){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(64.dp)
                                .padding(top = 50.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }

            }
        }
    }
}