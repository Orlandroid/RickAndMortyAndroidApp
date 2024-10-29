package com.rickandmortyorlando.orlando.features.filter_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.domain.models.local.SearchCharacter
import com.rickandmortyorlando.orlando.databinding.FragmentFilterDialogBinding

class FilterDialogFragment(
    val currentFilter: SearchCharacter,
    val searchInfo: (SearchCharacter) -> Unit
) : DialogFragment() {

    private lateinit var binding: FragmentFilterDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterDialogBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }


    fun setUpUi() {
        binding.main.setContent {
            FilterScreen(
                currentFilter = currentFilter,
                onSearchClicked = {
                    searchInfo.invoke(it)
                    dialog?.dismiss()
                }
            )
        }
    }

}