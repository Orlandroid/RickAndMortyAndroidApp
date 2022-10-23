package com.example.paggingexample.ui.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.databinding.FragmentMenuBinding
import com.example.paggingexample.ui.base.BaseFragment
import com.example.paggingexample.ui.extensions.click


class MenuFragment : BaseFragment<FragmentMenuBinding>(R.layout.fragment_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    @SuppressLint("SetTextI18n")
    override fun setUpUi() {
        with(binding) {
            imageCharacters.click {
                val action = MenuFragmentDirections.actionMenuFragmentToCharacterFragment()
                findNavController().navigate(action)
            }
        }
    }


}