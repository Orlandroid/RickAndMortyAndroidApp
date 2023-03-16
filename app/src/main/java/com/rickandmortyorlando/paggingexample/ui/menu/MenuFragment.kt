package com.rickandmortyorlando.paggingexample.ui.menu

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.databinding.FragmentMenuBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.episodes.EpisodesViewModel
import com.rickandmortyorlando.paggingexample.ui.extensions.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    private val viewModel: EpisodesViewModel by navGraphViewModels(R.id.main_graph){
        defaultViewModelProviderFactory
    }

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