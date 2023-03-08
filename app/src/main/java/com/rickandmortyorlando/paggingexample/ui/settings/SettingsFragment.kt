package com.rickandmortyorlando.paggingexample.ui.settings


import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.databinding.FragmentSettingsBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    private var settingsAdapter = SettingsAdapter()

    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarTitle.text = getText(R.string.settings)
        recyclerSettings.adapter = settingsAdapter
        setSettingsMenu()
    }

    private fun setSettingsMenu() {
        val setting = SettingsAdapter.Setting(getString(R.string.settings), true)
        val setting2 = SettingsAdapter.Setting(getString(R.string.settings), true)
        val setting3 = SettingsAdapter.Setting(getString(R.string.settings), true)
        settingsAdapter.setData(listOf(setting, setting2, setting3))
    }

}