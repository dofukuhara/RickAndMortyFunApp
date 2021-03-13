package com.fukuhara.rickandmortyfunapp.feature.episode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fukuhara.rickandmortyfunapp.R
import com.fukuhara.rickandmortyfunapp.feature.episode.di.EpisodeModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class EpisodeFragment : Fragment() {

    private val viewModel: EpisodeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(EpisodeModule.instance)

        return inflater.inflate(R.layout.episode_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()
        setupViewModelObservers(view)
    }

    private fun setupViewModelObservers(view: View) {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "loadingState: $it")
        }

        viewModel.isErrorState.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "isErrorState: $it")
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "errorMessage: $it")
        }

        viewModel.episodeList.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "Value: $it")
        }

        viewModel.nextPageState.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "nextPageState: $it")
        }

        viewModel.nextPage.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "nextPage: $it")
        }

        viewModel.previousPageState.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "previousPageState: $it")
        }

        viewModel.previousPage.observe(viewLifecycleOwner) {
            Log.i("FUKUHARALOG", "previousPage: $it")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unloadKoinModules(EpisodeModule.instance)
    }
}