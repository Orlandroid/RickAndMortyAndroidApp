package com.rickandmortyorlando.orlando.features.base


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.features.extensions.hideProgress
import com.rickandmortyorlando.orlando.features.extensions.setStatusBarColor

abstract class BaseFragment<ViewBinding : ViewDataBinding>(@LayoutRes protected val contentLayoutId: Int) :
    Fragment() {

    private var _binding: ViewBinding? = null

    protected val binding: ViewBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        return binding.root
    }

    open fun configureToolbar() = MainActivity.ToolbarConfiguration()

    open fun configSearchView() = MainActivity.SearchViewConfig()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
        setStatusBarColor(R.color.status_bar_color)
        (requireActivity() as MainActivity).apply {
            setToolbarConfiguration(configureToolbar())
            invalidateOptionsMenu()
            showSearchView(configSearchView())
        }
    }

    protected abstract fun setUpUi()

    open fun observerViewModel() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        hideProgress()
        setStatusBarColor(R.color.status_bar_color)
        (requireActivity() as MainActivity).changeToolbarColor(ColorDrawable(resources.getColor(R.color.status_bar_color)))
    }
}