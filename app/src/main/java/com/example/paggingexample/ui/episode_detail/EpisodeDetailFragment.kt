package com.example.paggingexample.ui.episode_detail

import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentEpisodeDetailBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click


class EpisodeDetailFragment :
    BaseFragment<FragmentEpisodeDetailBinding>(R.layout.fragment_episode_detail) {

    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        toolbarLayout.toolbarTitle.setText(R.string.episode_detail)
    }

}