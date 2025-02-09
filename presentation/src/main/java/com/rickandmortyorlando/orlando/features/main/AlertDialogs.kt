package com.rickandmortyorlando.orlando.features.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.rickandmortyorlando.orlando.R

import com.rickandmortyorlando.orlando.databinding.AlertDialogMessagesBinding
import com.rickandmortyorlando.orlando.features.extensions.gone
import com.rickandmortyorlando.orlando.features.extensions.visible


class AlertDialogs(
    private val kindOfMessage: Int = SUCCES_MESSAGE,
    private val messageBody: String,
    private val clikOnAccept: ClickOnAccept? = null,
    private val isTwoButtonDialog: Boolean = false
) :
    DialogFragment() {

    private lateinit var binding: AlertDialogMessagesBinding

    companion object {
        const val SUCCES_MESSAGE_COLOR = R.color.succes
        const val WARNING_MESSAGE_COLOR = R.color.waring
        const val ERROR_MESSAGE_COLOR = R.color.danger
        const val INFO_MESSAGE_COLOR = R.color.info
        const val SUCCES_MESSAGE = 0
        const val WARNING_MESSAGE = 1
        const val ERROR_MESSAGE = 2
        const val INFO_MESSAGE = 3

    }

    interface ClickOnAccept {
        fun clickOnAccept()
        fun clickOnCancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlertDialogMessagesBinding.inflate(layoutInflater, container, false)
        setUpUi()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun setUpUi() {
        with(binding) {
            buttonAceptarOneButton.setOnClickListener {
                clikOnAccept?.clickOnAccept()
                dialog?.dismiss()
            }
            buttonAceptar.setOnClickListener {
                clikOnAccept?.clickOnAccept()
                dialog?.dismiss()
            }
            buttonCancelar.setOnClickListener {
                clikOnAccept?.clickOnCancel()
                dialog?.dismiss()
            }
            binding.bodyMessage.text = messageBody
        }
        setKindOfMessage()
        setKindOfView(isTwoButtonDialog)
    }

    private fun setKindOfView(isTwoButtonDialog: Boolean) {
        if (isTwoButtonDialog) {
            with(binding) {
                buttonAceptarOneButton.gone()
                buttonAceptar.visible()
                buttonCancelar.visible()
            }
        } else {
            with(binding) {
                buttonAceptarOneButton.visible()
                buttonAceptar.gone()
                buttonCancelar.gone()
            }
        }
    }

    private fun setKindOfMessage() {
        when (kindOfMessage) {
            0 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(SUCCES_MESSAGE_COLOR))
                binding.titleHeader.text = getString(R.string.success)
            }

            1 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(WARNING_MESSAGE_COLOR))
                binding.titleHeader.text = getString(R.string.warning)
            }

            2 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(ERROR_MESSAGE_COLOR))
                binding.titleHeader.text = getString(R.string.error)
            }

            3 -> {
                binding.headerDialog.setCardBackgroundColor(resources.getColor(INFO_MESSAGE))
                binding.titleHeader.text = getString(R.string.info)
            }
        }
    }

}