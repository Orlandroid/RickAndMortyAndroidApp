package com.rickandmortyorlando.paggingexample.ui.menu

import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.databinding.FragmentMenuBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.ui.extensions.click


class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {


    override fun setUpUi() {
        with(binding) {
            imageCharacters.click {
                val action = MenuFragmentDirections.actionMenuFragmentToCharacterFragment()
                findNavController().navigate(action)
            }
            imageEpisiodos.click {
                val action = MenuFragmentDirections.actionMenuFragmentToEpisodesFragment()
                findNavController().navigate(action)
            }
            imageLocations.click {
                val action = MenuFragmentDirections.actionMenuFragmentToLocationsFragment()
                findNavController().navigate(action)
            }
        }
    }


}