package com.hustcaster.app.compose.rss

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hustcaster.app.R
import com.hustcaster.app.compose.common.NavigationBarImpl
import com.hustcaster.app.compose.component.PlayingBar
import com.hustcaster.app.viewmodels.RssViewModel

enum class RssScreenEvent{
    PODCAST_ALREADY_EXISTS,
    IS_GETTING_PODCAST,
    FINISH_GETTING_PODCAST
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RssScreen(
    rssViewModel: RssViewModel = hiltViewModel()
) {
    val scaffoldState= rememberBottomSheetScaffoldState()
    val currentValue = rssViewModel.rssUrlInput.collectAsState()
    val sharedFlow=rssViewModel.sharedFlow
    val isImporting= rssViewModel.isImporting.collectAsState()

    LaunchedEffect(sharedFlow) {
        sharedFlow.collect{
            when(it){
                RssScreenEvent.PODCAST_ALREADY_EXISTS -> {
                    scaffoldState.snackbarHostState.showSnackbar("Podcast already exists!")
                }
                RssScreenEvent.IS_GETTING_PODCAST -> {
                    scaffoldState.snackbarHostState.showSnackbar("Start fetching podcast, please wait...")
                }
                RssScreenEvent.FINISH_GETTING_PODCAST -> {
                    scaffoldState.snackbarHostState.showSnackbar("Finish! Please go to home screen to check it out!")
                }
            }
        }
    }

    Scaffold(
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
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center
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
            }
        }
    }
}