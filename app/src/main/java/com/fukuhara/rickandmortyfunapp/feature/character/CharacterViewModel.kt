package com.fukuhara.rickandmortyfunapp.feature.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fukuhara.common.arch.Either
import com.fukuhara.common.data.PageIndicator
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterRepository
import com.fukuhara.rickandmortyfunapp.feature.character.business.CharacterResultModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(
    private val repository: CharacterRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var currentPageIndexFetch = "1"

    // Handle Loading/Progress State
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    // Handle Error State
    private val _isErrorState = MutableLiveData<Boolean>()
    val isErrorState: LiveData<Boolean> = _isErrorState
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Store List of Characters
    private val _characterList = MutableLiveData<List<CharacterResultModel>>()
    val characterList: LiveData<List<CharacterResultModel>> = _characterList

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

    private val _pageIndicator = MutableLiveData<PageIndicator>()
    val pageIndicator: LiveData<PageIndicator> = _pageIndicator

    fun refresh() {
        getData(currentPageIndexFetch)
    }

    fun getData(pageIndex: String = "1") {
        currentPageIndexFetch = pageIndex

        viewModelScope.launch {
            _loadingState.value = true

            val characterResult = withContext(backgroundDispatcher) {
                repository.getData(pageIndex)
            }

            _loadingState.value = false

            when(characterResult) {
                is Either.Left -> {
                    _isErrorState.value = true
                    _errorMessage.value = characterResult.exception.message
                }
                is  Either.Right -> {
                    _isErrorState.value = false

                    characterResult.data.run {
                        _characterList.value = this.results

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

                        _pageIndicator.value = PageIndicator(pageIndex, this.info.pages.toString())
                    }
                }
            }
        }
    }
}