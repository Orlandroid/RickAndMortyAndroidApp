package com.example.paggingexample.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.paggingexample.MainActivity
import com.example.paggingexample.R
import com.example.paggingexample.data.state.ApiState
import com.example.paggingexample.ui.main.AlertDialogs

fun Fragment.showProgress() {
    (requireActivity() as MainActivity).showProgress()
}

fun Fragment.hideProgress() {
    (requireActivity() as MainActivity).hideProgress()
}

fun Fragment.shouldShowProgress(isLoading: Boolean) {
    (requireActivity() as MainActivity).shouldShowProgress(isLoading)
}

fun Fragment.showSuccessMessage(messageSuccess: String = getString(R.string.message_succes)) {
    val dialog = AlertDialogs(
        kindOfMessage = AlertDialogs.SUCCES_MESSAGE,
        messageBody = messageSuccess
    )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorApi(
    shouldCloseTheViewOnApiError: Boolean = false,
    messageBody: String = getString(R.string.error_al_obtener_datos)
) {
    val dialog = AlertDialogs(
        kindOfMessage = AlertDialogs.ERROR_MESSAGE,
        messageBody = messageBody,
        clikOnAccept = object : AlertDialogs.ClickOnAccept {
            override fun clickOnAccept() {
                if (shouldCloseTheViewOnApiError) {
                    findNavController().popBackStack()
                }
            }

            override fun clickOnCancel() {

            }

        })
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}

fun Fragment.showErrorNetwork(shouldCloseTheViewOnApiError: Boolean = false) {
    val dialog =
        AlertDialogs(
            kindOfMessage = AlertDialogs.ERROR_MESSAGE,
            messageBody = getString(R.string.error_internet),
            clikOnAccept = object : AlertDialogs.ClickOnAccept {
                override fun clickOnAccept() {
                    if (shouldCloseTheViewOnApiError) {
                        findNavController().popBackStack()
                    }
                }

                override fun clickOnCancel() {

                }
            }
        )
    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
}


fun <T> Fragment.observeApiResultGeneric(
    liveData: LiveData<ApiState<T>>,
    onLoading: () -> Unit = { },
    onFinishLoading: () -> Unit = { },
    hasProgressTheView: Boolean = false,
    shouldCloseTheViewOnApiError: Boolean = false,
    onError: (() -> Unit)? = null,
    noData: () -> Unit = {},
    onSuccess: (data: T) -> Unit,
) {
    liveData.observe(viewLifecycleOwner) { apiState ->
        if (apiState == null) return@observe
        fun handleStatus(isLoading: Boolean) {
            if (isLoading) {
                onLoading()
            } else {
                onFinishLoading()
            }
        }

        val isLoading = apiState is ApiState.Loading
        if (hasProgressTheView) {
            shouldShowProgress(isLoading)
        } else {
            handleStatus(isLoading)
        }
        when (apiState) {
            is ApiState.Success -> {
                if (apiState.data != null) {
                    onSuccess(apiState.data)
                }
            }
            is ApiState.Error -> {
                if (onError == null) {
                    showErrorApi(shouldCloseTheViewOnApiError)
                } else {
                    onError()
                }
            }
            is ApiState.ErrorNetwork -> {
                showErrorNetwork(shouldCloseTheViewOnApiError)
            }
            is ApiState.NoData -> {
                noData()
            }
            else -> {}
        }
    }
}

