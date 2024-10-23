package com.rickandmortyorlando.orlando.features.settings


import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSettingsBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    private var settingsAdapter =
        SettingsAdapter(
            clickOnSetting = { clickOnSetting(it) },
            changeSwitch = { changeTheme(it) }
        )

    @Inject
    lateinit var rickAndMortyPreferences: RickAndMortyPreferences
    override fun setUpUi() = with(binding) {
        recyclerSettings.adapter = settingsAdapter
        setSettingsMenu()
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.settings)
    )


    private fun setSettingsMenu() {
        val setting = SettingsAdapter.Setting(getString(R.string.change_theme), true)
        settingsAdapter.setData(listOf(setting), rickAndMortyPreferences.getIsNightMode())
    }

    private fun changeTheme(isNightMode: Boolean) {
        rickAndMortyPreferences.saveIsNightMode(isNightMode)
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }

    private fun clickOnSetting(setting: SettingsAdapter.Setting) {

    }


}