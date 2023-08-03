package com.rickandmortyorlando.orlando.ui.locations

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.databinding.FragmentLocationsBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.gone
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.myOnScrolled
import com.rickandmortyorlando.orlando.ui.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.ui.extensions.showProgress
import com.rickandmortyorlando.orlando.ui.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    private val viewModel: LocationsViewModel by viewModels()
    private var adapter = LocationsAdapter { clickOnLocation(it) }
    private var currentPage = 1
    private var totalPages = 0
    private var canCallToTheNextPage = true
    private var locationsList: ArrayList<SingleLocation> = arrayListOf()
    private var isFirsTimeOneTheView = true

    override fun setUpUi() = with(binding) {
        if (isFirsTimeOneTheView) {
            resetPaging()
            viewModel.getLocations(currentPage)
            isFirsTimeOneTheView = false
        }
        recyclerView.adapter = adapter
        recyclerView.myOnScrolled {
            if (!canCallToTheNextPage) return@myOnScrolled
            if (totalPages > currentPage) {
                currentPage++
                canCallToTheNextPage = false
                viewModel.getLocations(page = currentPage)
            }
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.locations)
    )


    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            viewModel.locationResponse,
            onLoading = { onLoading() },
            onFinishLoading = { onFinishLoading() }) {
            locationsList.addAll(it.results)
            adapter.setData(locationsList)
            canCallToTheNextPage = true
            totalPages = it.info.pages
        }
    }

    private fun onLoading() {
        if (currentPage > 1) {
            showProgress()
            return
        }
        binding.skeleton.visible()
    }

    private fun onFinishLoading() {
        if (currentPage > 1) {
            hideProgress()
            return
        }
        binding.skeleton.gone()
    }

    private fun clickOnLocation(locationId: Int) {
        val action =
            LocationsFragmentDirections.actionLocationsFragmentToLocationDetailFragment(locationId)
        findNavController().navigate(action)
    }

    private fun resetPaging() {
        locationsList.clear()
        currentPage = 1
    }

}