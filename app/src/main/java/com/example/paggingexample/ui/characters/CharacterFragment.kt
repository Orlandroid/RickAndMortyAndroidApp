package com.example.paggingexample.ui.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.data.models.character.Character
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.databinding.FragmentCharacterBinding
import com.example.paggingexample.ui.extensions.click
import com.example.paggingexample.ui.extensions.gone
import com.example.paggingexample.ui.extensions.myOnScrolled
import com.example.paggingexample.ui.extensions.visible
import com.example.paggingexample.ui.main.AlertDialogs
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
        resetPaging()
        viewModel.getCharacters(page.toString())
        with(binding) {
            toolbarLayout.toolbarTitle.text = "Characters"
            toolbarLayout.toolbarBack.click {
                findNavController().popBackStack()
            }
            recyclerView.adapter = adapter
            adapter.setListener(object : CharacterAdapter.ClickOnCharacter {
                override fun clickOnCharacter(character: Character) {
                    val action =
                        CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                            character.id
                        )
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
                }
            }
        }
    }

    private fun resetPaging() {
        charactesrList.clear()
        page = 1
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserves() {
        viewModel.myCharacterResponse.observe(viewLifecycleOwner) { apiState ->
            apiState?.let {
                if (page > 1) {
                    binding.progressBar.isVisible = apiState is ApiState.Loading
                }
                when (apiState) {
                    is ApiState.Success -> {
                        if (apiState.data != null) {
                            charactesrList.addAll(apiState.data.results)
                            totalPages = apiState.data.info.pages
                            adapter.setData(charactesrList)
                            canCallToTheNextPage = true
                            binding.skeleton.gone()
                            binding.root.setBackgroundColor(resources.getColor(R.color.background))
                        }
                    }
                    is ApiState.Error -> {
                        val dialog =
                            AlertDialogs(AlertDialogs.ERROR_MESSAGE, "Error al obtener datos")
                        activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                        findNavController().popBackStack()
                    }
                    is ApiState.ErrorNetwork -> {
                        val dialog =
                            AlertDialogs(
                                AlertDialogs.ERROR_MESSAGE,
                                "Verifica tu conexion de internet"
                            )
                        activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}