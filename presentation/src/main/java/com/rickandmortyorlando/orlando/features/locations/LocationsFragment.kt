package com.rickandmortyorlando.orlando.features.locations

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentLocationsBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.getError
import com.rickandmortyorlando.orlando.features.extensions.hideProgress
import com.rickandmortyorlando.orlando.features.extensions.showError
import com.rickandmortyorlando.orlando.features.extensions.showErrorApi
import com.rickandmortyorlando.orlando.features.extensions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    private val viewModel: LocationsViewModel by viewModels()
    private var adapter = LocationsAdapter { clickOnLocation(it) }

    override fun setUpUi() = with(binding) {
        showProgress()
        recyclerView.adapter = adapter
        getLocations()
        listenerAdapter()
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.locations)
    )

    private fun getLocations() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getLocationsPagingSource.collectLatest { locations ->
                    adapter.submitData(locations)
                }
            }
        }
    }

    private fun listenerAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.addLoadStateListener { loadState ->
                    if (loadState.source.append is LoadState.Loading || loadState.source.refresh is LoadState.Loading) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                    val errorState = loadState.getError()
                    errorState?.showError {
                        showErrorApi()
                    }
                }
            }
        }
    }


    private fun clickOnLocation(locationId: Int) {
        val action =
            LocationsFragmentDirections.actionLocationsFragmentToLocationDetailFragment(locationId)
        findNavController().navigate(action)
    }

}