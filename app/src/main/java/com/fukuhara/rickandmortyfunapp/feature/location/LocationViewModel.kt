package com.fukuhara.rickandmortyfunapp.feature.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fukuhara.common.arch.Either
import com.fukuhara.rickandmortyfunapp.common.PageIndicator
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationRepository
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationResultModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel(
    private val repository: LocationRepository,
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

    // Store List of Locations
    private val _locationList = MutableLiveData<List<LocationResultModel>>()
    val locationList: LiveData<List<LocationResultModel>> = _locationList

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

            val locationResult = withContext(backgroundDispatcher) {
                repository.getData(pageIndex)
            }

            _loadingState.value = false

            when (locationResult) {
                is Either.Left -> {
                    _isErrorState.value = true
                    _errorMessage.value = locationResult.exception.message
                }
                is Either.Right -> {
                    _isErrorState.value = false

                    locationResult.data.run {
                        _locationList.value = this.results

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