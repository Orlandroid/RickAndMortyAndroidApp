package com.rickandmortyorlando.orlando.features.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.preferences.RickAndMortyPreferences
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentSettingsBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.extensions.content
import com.rickandmortyorlando.orlando.theme.AlwaysWhite
import com.rickandmortyorlando.orlando.theme.Background
import com.rickandmortyorlando.orlando.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {


    @Inject
    lateinit var rickAndMortyPreferences: RickAndMortyPreferences

    override fun setUpUi() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return content {
            SettingsScreen(isNightModeEnable = rickAndMortyPreferences.getIsNightMode())
        }
    }

    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        showToolbar = true,
        toolbarTitle = getString(R.string.settings)
    )


    @Composable
    fun SettingsScreen(modifier: Modifier = Modifier, isNightModeEnable: Boolean) {
        val isChecked = remember { mutableStateOf(isNightModeEnable) }
        Column(
            modifier
                .background(Background)
                .fillMaxSize()
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        text = stringResource(R.string.change_theme)
                    )
                    Switch(
                        modifier = Modifier.weight(1f),
                        checked = isChecked.value,
                        onCheckedChange = {
                            isChecked.value = !isChecked.value
                            changeTheme(isChecked.value)
                        }
                    )
                }
            }
        }
    }

    private fun changeTheme(isNightMode: Boolean) {
        rickAndMortyPreferences.saveIsNightMode(isNightMode)
        ThemeUtils.themeUtils.setNightMode(isNightMode)
    }


    @Composable
    @Preview(showBackground = true)
    fun SettingsScreenPreview(modifier: Modifier = Modifier) {
        SettingsScreen(isNightModeEnable = false)
    }

}