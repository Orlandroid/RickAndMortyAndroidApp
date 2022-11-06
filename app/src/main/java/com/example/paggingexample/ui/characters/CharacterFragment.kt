package com.example.paggingexample.ui.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.R
import com.example.paggingexample.data.models.character.Character
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.databinding.FragmentCharacterBinding
import com.example.paggingexample.ui.extensions.*
import com.example.paggingexample.ui.main.AlertDialogs
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.Query


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
        enableToolbarForListeners(binding.toolbarLayout.toolbar)
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


    private fun setUpObserves() {
        observeCharactersResponse()
        observeSearchCharacters()
    }

    private fun observeCharactersResponse() {
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

    private fun observeSearchCharacters() {
        viewModel.searchCharacterResponse.observe(viewLifecycleOwner) { apiState ->
            apiState?.let {
                when (apiState) {
                    is ApiState.Success -> {
                        if (apiState.data != null) {

                        }
                    }
                    is ApiState.Error -> {
                        if (apiState.codeError == 404) {
                            requireContext().showToast("Character not found")
                        } else {
                            val dialog =
                                AlertDialogs(AlertDialogs.ERROR_MESSAGE, "Error al obtener datos")
                            activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                            findNavController().popBackStack()
                        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        myOnCreateOptionsMenu(
            menu = menu,
            myOnQueryTextChange = { myOnQueryTextChange(it) },
            myOnQueryTextSubmit = { myOnQueryTextSubmit(it) }
        )
    }

    private fun myOnQueryTextChange(textChange: String) {
        Log.w("ANDROID", textChange)
    }

    private fun myOnQueryTextSubmit(query: String) {
        Log.w("ANDROID SUBMIT", query)
        viewModel.searchCharacters(name = query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}