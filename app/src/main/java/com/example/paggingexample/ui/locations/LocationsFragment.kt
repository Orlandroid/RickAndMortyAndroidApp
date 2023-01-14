package com.example.paggingexample.ui.locations

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.data.models.remote.location.SingleLocation
import com.example.paggingexample.databinding.FragmentLocationsBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.myOnScrolled
import com.example.paggingexample.ui.extensions.observeApiResultGeneric
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
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.text = getString(R.string.locations)
        viewModel.getLocations(currentPage)
        adapter = LocationsAdapter()
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