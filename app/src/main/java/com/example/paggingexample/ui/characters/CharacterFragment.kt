package com.example.paggingexample.ui.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.data.models.Character
import com.example.paggingexample.databinding.FragmentCharacterBinding
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.gone
import com.example.paggingexample.ui.extensions.myOnScrolled
import com.example.paggingexample.ui.extensions.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterViewModel by viewModels()
    private var page = 1
    private var totalPages = 0
    private val adapter = CharacterAdapter()
    private var canCallToTheNextPage = true
    private var charactesrList: ArrayList<Character> = arrayListOf()
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
        charactesrList.clear()
        viewModel.getCharacters(page.toString())
        binding.progressBar.visible()
        with(binding) {
            recyclerView.adapter = adapter
            adapter.setListener(object : CharacterAdapter.ClickOnCharacter {
                override fun clickOnCharacter(character: Character) {
                    val action =
                        CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(character.id)
                    findNavController().navigate(action)
                }
            })
            recyclerView.myOnScrolled {
                if (!canCallToTheNextPage) {
                    return@myOnScrolled
                }
                if (totalPages > page) {
                    page++
                    canCallToTheNextPage = false
                    viewModel.getCharacters(page = page.toString())
                    binding.progressBar.visible()
                    Log.w("PAGE", "Current page call $page")
                }
            }
            toolbarLayout.toolbarTitle.text = "Characters"
            toolbarLayout.toolbarBack.click {
                findNavController().popBackStack()
            }
            toolbarLayout.tvInfo.visible()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserves() {
        viewModel.characterResponse.observe(viewLifecycleOwner) {
            charactesrList.addAll(it.results)
            totalPages = it.info.pages
            adapter.setData(charactesrList)
            canCallToTheNextPage = true
            binding.progressBar.gone()
            binding.toolbarLayout.tvInfo.text = "Total -> ${charactesrList.size}"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}