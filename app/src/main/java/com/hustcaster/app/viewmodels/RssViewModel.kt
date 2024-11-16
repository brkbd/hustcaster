package com.hustcaster.app.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.network.parser.MainParser
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RssViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val _rssUrlInput = MutableStateFlow("")
    val rssUrlInput = _rssUrlInput.asStateFlow()

    fun onValueChange(newString: String) {
        _rssUrlInput.value = newString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun importPodcast() {
        viewModelScope.launch {
            MainParser.parse(_rssUrlInput.value,episodeRepository,podcastRepository)
        }
    }
}