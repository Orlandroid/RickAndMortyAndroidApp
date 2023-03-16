package com.rickandmortyorlando.paggingexample.ui.locations

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.data.models.remote.location.SingleLocation
import com.rickandmortyorlando.paggingexample.databinding.FragmentLocationsBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.extensions.click
import com.rickandmortyorlando.paggingexample.ui.extensions.myOnScrolled
import com.rickandmortyorlando.paggingexample.ui.extensions.observeApiResultGeneric
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : BaseFragment<FragmentLocationsBinding>(R.layout.fragment_locations) {

    private val viewModel: LocationsViewModel by viewModels()
    private var adapter: LocationsAdapter? = null
    private var currentPage = 1
    private var totalPages = 0
    private var canCallToTheNextPage = true
    private var locationsList: ArrayList<SingleLocation> = arrayListOf()

    override fun setUpUi() = with(binding) {
        resetPaging()
        viewModel.getLocations(currentPage)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.text = getString(R.string.locations)
        adapter = LocationsAdapter {
            val action =
                LocationsFragmentDirections.actionLocationsFragmentToLocationDetailFragment(it)
            findNavController().navigate(action)
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

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(viewModel.locationResponse, hasProgressTheView = true) {
            locationsList.addAll(it.results)
            adapter?.setData(locationsList)
            canCallToTheNextPage = true
            totalPages = it.info.pages
        }
    }

    private fun resetPaging() {
        locationsList.clear()
        currentPage = 1
    }

}