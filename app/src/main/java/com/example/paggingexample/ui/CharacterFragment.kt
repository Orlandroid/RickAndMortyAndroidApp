package com.example.paggingexample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.paggingexample.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : Fragment() {


    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()
    private val page = 1
    private val adapter = CharacterAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObserves()
        return binding.root
    }


    private fun setUpUi() {
        viewModel.getCharacters(page.toString())
        with(binding) {
            recyclerView.adapter = adapter
        }
    }

    private fun setUpObserves() {
        viewModel.characterResponse.observe(viewLifecycleOwner) {
            adapter.setData(it.results)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}