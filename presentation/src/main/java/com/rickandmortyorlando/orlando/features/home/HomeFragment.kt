package com.rickandmortyorlando.orlando.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentMenuBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.features.extensions.content
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    private val viewModel: EpisodesViewModel by navGraphViewModels(R.id.main_graph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            HomeScreen(
                clickOnCharacters = {
                    viewModel.comesFromEpisodesMainMenu = false
                    val action =
                        HomeFragmentDirections.actionMenuFragmentToCharacterFragmentWrapper()
                    findNavController().navigate(action)
                },
                clickOnEpisodes = {
                    viewModel.comesFromEpisodesMainMenu = true
                    findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToEpisodesFragmentWrapper())
                },
                clickOnLocation = {
                    findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToLocationsFragmentWrapper())
                }
            )
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showBackArrow = false,
        showToolbar = true,
        toolbarTitle = getString(R.string.home)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showConfigIcon = true,
        clickOnConfigIcon = {
            findNavController().navigate(HomeFragmentDirections.actionMenuFragmentToSettingsFragment())
        }
    )

    override fun setUpUi() {

    }


}