package com.example.paggingexample.ui.menu

import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentMenuBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click


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