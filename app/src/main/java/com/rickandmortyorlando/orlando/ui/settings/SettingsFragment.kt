package com.rickandmortyorlando.orlando.ui.settings


import androidx.navigation.fragment.findNavController
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.databinding.FragmentSettingsBinding
import com.rickandmortyorlando.orlando.ui.base.BaseFragment
import com.rickandmortyorlando.orlando.ui.extensions.click
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
        toolbarLayout.toolbarTitle.text = getText(R.string.settings)
        toolbarLayout.toolbarBack.click {
            findNavController().popBackStack()
        }
        recyclerSettings.adapter = settingsAdapter
        setSettingsMenu()
    }


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