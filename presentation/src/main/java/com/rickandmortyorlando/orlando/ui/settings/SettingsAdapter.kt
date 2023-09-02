package com.rickandmortyorlando.orlando.ui.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmortyorlando.orlando.databinding.ItemSettingBinding
import com.rickandmortyorlando.orlando.ui.extensions.click
import com.rickandmortyorlando.orlando.ui.extensions.gone
import com.rickandmortyorlando.orlando.ui.extensions.visible


class SettingsAdapter(
    private val clickOnSetting: (Setting) -> Unit = {},
    private val changeSwitch: (isCheck: Boolean) -> Unit = {}
) :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    private var settingsList = listOf<Setting>()
    private var isNightModeEnable: Boolean = false


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Setting>, isNightMode: Boolean) {
        isNightModeEnable = isNightMode
        settingsList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(setting: Setting) = with(binding) {
            textViewSettingName.text = setting.nameSetting
            if (setting.showSwitch) {
                switchTheme.visible()
            } else {
                switchTheme.gone()
            }
            switchTheme.isChecked = isNightModeEnable
            switchTheme.setOnCheckedChangeListener { _, isCheck ->
                changeSwitch(isCheck)
            }
            itemView.click {
                clickOnSetting(setting)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemSettingBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(settingsList[position])
    }

    override fun getItemCount() = settingsList.size

    data class Setting(val nameSetting: String, val showSwitch: Boolean = false)
}
