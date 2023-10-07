package com.rickandmortyorlando.orlando.ui.menu

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentMenuBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.episodes.EpisodesViewModel
import com.rickandmortyorlando.orlando.ui.extensions.click
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
        })

    override fun setUpUi() = with(binding) {
        imageCharacters.click {
            viewModel.comesFromEpisodesMainMenu = false
            val action = MenuFragmentDirections.actionMenuFragmentToCharacterFragment()
            findNavController().navigate(action)
        }
        imageEpisiodos.click {
            viewModel.comesFromEpisodesMainMenu = true
            val action = MenuFragmentDirections.actionMenuFragmentToEpisodesFragment()
            findNavController().navigate(action)
        }
        imageLocations.click {
            val action = MenuFragmentDirections.actionMenuFragmentToLocationsFragment()
            findNavController().navigate(action)
        }
    }


}