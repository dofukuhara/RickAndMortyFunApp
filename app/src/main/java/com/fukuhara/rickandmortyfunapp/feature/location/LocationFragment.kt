package com.fukuhara.rickandmortyfunapp.feature.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.fukuhara.designsystem.ErrorFeedback
import com.fukuhara.designsystem.PageNavigator
import com.fukuhara.rickandmortyfunapp.R
import com.fukuhara.rickandmortyfunapp.feature.location.adapter.LocationAdapter
import com.fukuhara.rickandmortyfunapp.feature.location.di.LocationModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class LocationFragment : Fragment() {
    private val viewModel: LocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(LocationModule.instance)

        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()
        setupViewModelObservers(view)
    }

    private fun setupViewModelObservers(view: View) {
        val pageNavigator: PageNavigator = view.findViewById(R.id.location_page_indicator)
        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.location_progress_bar)
        val recyclerView: RecyclerView = view.findViewById(R.id.location_recyclerview)
        val errorFeedback = ErrorFeedback(view)

        recyclerView.setHasFixedSize(true)

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            // Preventing Buttons to be clicked while in Loading State
            pageNavigator.setButtonsClickableState(!isLoading)

            when (isLoading) {
                true -> progressBar.show()
                false -> progressBar.hide()
            }
        }

        viewModel.isErrorState.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    pageNavigator.hideComponents()
                    recyclerView.visibility = View.INVISIBLE
                    errorFeedback.showErrorFeedbackScreen()
                    errorFeedback.setOnRetryButtonListener {
                        viewModel.refresh()
                    }
                }
                false -> {
                    errorFeedback.hideErrorFeedbackScreen()
                    pageNavigator.showComponents()
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            errorFeedback.setErrorMessage(it)
        }

        viewModel.locationList.observe(viewLifecycleOwner) {
            val adapter = LocationAdapter(it)
            recyclerView.adapter = adapter
        }

        viewModel.pageIndicator.observe(viewLifecycleOwner) {
            pageNavigator.setPageIndicatorText(it.currentPage, it.totalPages)
        }

        viewModel.nextPageState.observe(viewLifecycleOwner) { isEnabled ->
            pageNavigator.setRightButtonVisibility(isEnabled)
        }

        viewModel.nextPage.observe(viewLifecycleOwner) { nextPageIndex ->
            pageNavigator.onRightButtonClickListener {
                viewModel.getData(nextPageIndex)
            }
        }

        viewModel.previousPageState.observe(viewLifecycleOwner) { isEnabled ->
            pageNavigator.setLeftButtonVisibility(isEnabled)
        }

        viewModel.previousPage.observe(viewLifecycleOwner) { previousPageIndex ->
            pageNavigator.onLeftButtonClickListener {
                viewModel.getData(previousPageIndex)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(LocationModule.instance)
    }
}