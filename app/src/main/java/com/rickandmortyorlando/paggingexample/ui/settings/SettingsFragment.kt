package com.rickandmortyorlando.paggingexample.ui.settings


import com.rickandmortyorlando.paggingexample.R
import com.rickandmortyorlando.paggingexample.databinding.FragmentSettingsBinding
import com.rickandmortyorlando.paggingexample.ui.base.BaseFragment
import com.rickandmortyorlando.paggingexample.utils.ThemeUtils


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    private var settingsAdapter = SettingsAdapter { changeTheme(it.showSwitch) }

    override fun setUpUi() = with(binding) {
        toolbarLayout.toolbarTitle.text = getText(R.string.settings)
        recyclerSettings.adapter = settingsAdapter
        setSettingsMenu()
    }

    private fun setSettingsMenu() {
        val setting = SettingsAdapter.Setting(getString(R.string.change_theme), true)
        settingsAdapter.setData(listOf(setting))
    }

    private fun changeTheme(isNightMode: Boolean) {
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }

}