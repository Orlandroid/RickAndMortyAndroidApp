package com.rickandmortyorlando.orlando.features.episode_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.navArgs
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentEpisodeDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content

class EpisodeDetailFragmentWrapper :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        toolbarTitle = getString(R.string.location),
        showToolbar = true
    )


    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            val viewModel: EpisodeDetailViewModel = hiltViewModel()
            EpisodeDetailScreen()
        }
    }


    override fun setUpUi() {

    }

}