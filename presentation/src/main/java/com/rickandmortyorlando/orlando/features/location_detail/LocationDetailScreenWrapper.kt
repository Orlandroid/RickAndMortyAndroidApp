package com.rickandmortyorlando.orlando.features.location_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentLocationDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content

class LocationDetailScreenWrapper :
    BaseFragment<FragmentLocationDetailBinding>(R.layout.fragment_location_detail) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        toolbarTitle = getString(R.string.location),
        showToolbar = true
    )


    private val args: LocationDetailScreenWrapperArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            LocationDetailScreen(
                idLocation = args.locationDetail,
                clickOnCharacter = ::clickOnCharacter
            )
        }
    }

    private fun clickOnCharacter(characterId: Int) {
        findNavController().navigate(
            LocationDetailScreenWrapperDirections.navigationToCharacterDetailWrapper(
                characterId
            )
        )
    }

    override fun setUpUi() {

    }
}