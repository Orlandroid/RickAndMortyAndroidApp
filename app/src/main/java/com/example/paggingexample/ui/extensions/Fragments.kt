package com.example.paggingexample.ui.extensions

import android.util.Log
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
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

fun Fragment.getPackageName():String = context?.packageName.toString()


fun Fragment.navigateAction(action: NavDirections) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action)
    }
}

fun Fragment.setStatusBarColor(@ColorRes colorStatusCharacter: Int) {
    val window = activity?.window
    window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window?.statusBarColor = ContextCompat.getColor(requireActivity(), colorStatusCharacter)
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
                Log.w(context?.packageName, apiState.message.toString())
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

