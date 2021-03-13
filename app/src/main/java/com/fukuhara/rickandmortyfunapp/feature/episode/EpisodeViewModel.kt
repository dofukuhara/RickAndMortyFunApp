package com.fukuhara.rickandmortyfunapp.feature.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fukuhara.common.arch.Either
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeRepository
import com.fukuhara.rickandmortyfunapp.feature.episode.business.EpisodeResultModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeViewModel(
    private val repository: EpisodeRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    // Handle Loading/Progress State
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // Handle Error State
    private val _isErrorState = MutableLiveData<Boolean>()
    val isErrorState: LiveData<Boolean> = _isErrorState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Store List of Episodes
    private val _episodeList = MutableLiveData<List<EpisodeResultModel>>()
    val episodeList: LiveData<List<EpisodeResultModel>> = _episodeList

    // Handle Next Button State and Destination
    private val _nextPage = MutableLiveData<String>()
    val nextPage: LiveData<String> = _nextPage
    private val _nextPageState = MutableLiveData<Boolean>()
    val nextPageState: LiveData<Boolean> = _nextPageState

    // Handle Previous Button State and Destination
    private val _previousPage = MutableLiveData<String>()
    val previousPage: LiveData<String> = _previousPage
    private val _previousPageState = MutableLiveData<Boolean>()
    val previousPageState: LiveData<Boolean> = _previousPageState

    fun getData(pageIndex: String = "1") {
        viewModelScope.launch {
            _loadingState.value = true

            val episodeResult = withContext(backgroundDispatcher) {
                repository.getData(pageIndex)
            }

            _loadingState.value = false

            when (episodeResult) {
                is Either.Left -> {
                    _isErrorState.value = true
                    _errorMessage.value = episodeResult.exception.message
                }
                is Either.Right -> {
                    _isErrorState.value = false
                    episodeResult.data.run {
                        _episodeList.value = this.results

                        this.info.next?.let {
                            _nextPage.value = it
                            _nextPageState.value = true
                        } ?: run {
                            _nextPageState.value = false
                        }

                        this.info.prev?.let {
                            _previousPage.value = it
                            _previousPageState.value = true
                        } ?: run {
                            _previousPageState.value = false
                        }
                    }
                }
            }
        }
    }
}