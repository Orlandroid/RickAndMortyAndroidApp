package com.rickandmortyorlando.orlando.features.menu

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentMenuBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.features.extensions.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    private val viewModel: EpisodesViewModel by navGraphViewModels(R.id.main_graph) {
        defaultViewModelProviderFactory
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showBackArrow = false,
        showToolbar = true,
        toolbarTitle = getString(R.string.home)
    )

    override fun configSearchView() = MainActivity.SearchViewConfig(
        showConfigIcon = true,
        clickOnConfigIcon = {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToSettingsFragment())
        }
    )

    override fun setUpUi() = with(binding) {
        imageCharacters.click {
            viewModel.comesFromEpisodesMainMenu = false
            val action = MenuFragmentDirections.actionMenuFragmentToCharacterFragment()
            findNavController().navigate(action)
        }
        imageEpisiodos.click {
            viewModel.comesFromEpisodesMainMenu = true
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToEpisodesFragment())
        }
        imageLocations.click {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToLocationsFragment())
        }
    }


}