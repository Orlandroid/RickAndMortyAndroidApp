package com.example.paggingexample.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.paggingexample.R
import com.example.paggingexample.ui.extensions.hideProgress
import com.example.paggingexample.ui.extensions.setStatusBarColor

abstract class BaseFragment<ViewBinding : ViewDataBinding>(@LayoutRes protected val contentLayoutId: Int) :
    Fragment() {

    protected lateinit var binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
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
        hideProgress()
        setStatusBarColor(R.color.status_bar_color)
    }
}