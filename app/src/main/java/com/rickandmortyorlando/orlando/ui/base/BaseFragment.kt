package com.rickandmortyorlando.orlando.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.ui.extensions.hideProgress
import com.rickandmortyorlando.orlando.ui.extensions.setStatusBarColor

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observerViewModel()
        setStatusBarColor(R.color.status_bar_color)
    }

    protected abstract fun setUpUi()

    open fun observerViewModel() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        hideProgress()
        setStatusBarColor(R.color.status_bar_color)
    }
}