package com.rickandmortyorlando.orlando.features.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.orlando.MainActivity


fun Fragment.changeToolbarTitle(title: String) {
    (requireActivity() as MainActivity).changeTitleToolbar(title)
}

fun Fragment.content(content: @Composable () -> Unit): ComposeView {
    return ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent(content)
    }
}


fun Fragment.navigateAction(action: NavDirections) {
    val navController = this.findNavController()
    if (navController.currentDestination?.getAction(action.actionId) == null) {
        return
    } else {
        navController.navigate(action)
    }
}
