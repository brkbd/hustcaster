package com.hustcaster.app.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.network.parser.MainParser
import com.hustcaster.app.network.parser.ParseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RssViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val _rssUrlInput = MutableStateFlow("")
    val rssUrlInput = _rssUrlInput.asStateFlow()

    private val _sharedFlow= MutableSharedFlow<RssScreenEvent>()
    val sharedFlow=_sharedFlow

    private val _isImporting=MutableStateFlow(false)
    val isImporting=_isImporting.asStateFlow()

    fun onValueChange(newString: String) {
        _rssUrlInput.value = newString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onImportClick(){
        viewModelScope.launch {
            if (isPodcastExists()){
                _sharedFlow.emit(RssScreenEvent.PODCAST_ALREADY_EXISTS)
            }else{
                _sharedFlow.emit(RssScreenEvent.IS_GETTING_PODCAST)
                _isImporting.value=true
                if(importPodcast()==ParseResult.SUCCESS){
                    _isImporting.value=false
                    _sharedFlow.emit(RssScreenEvent.FINISH_GETTING_PODCAST)
                }else{
                    _isImporting.value=false
                    _sharedFlow.emit(RssScreenEvent.FAILED_PARSING_DATA)
                }
            }
        }
    }

    private suspend fun isPodcastExists(): Boolean {
        return withContext(Dispatchers.IO) {
            podcastRepository.getPodcastIdByRssUrl(_rssUrlInput.value)!= 0L
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun importPodcast():ParseResult =
        MainParser.parse(_rssUrlInput.value,episodeRepository,podcastRepository)
}

enum class RssScreenEvent{
    PODCAST_ALREADY_EXISTS,
    IS_GETTING_PODCAST,
    FINISH_GETTING_PODCAST,
    FAILED_PARSING_DATA
}