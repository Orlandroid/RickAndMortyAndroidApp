package com.rickandmortyorlando.paggingexample.ui.settings

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmortyorlando.paggingexample.databinding.ItemSettingBinding
import com.rickandmortyorlando.paggingexample.ui.extensions.gone
import com.rickandmortyorlando.paggingexample.ui.extensions.visible

class SettingsAdapter :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    private var settingsList = listOf<Setting>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Setting>) {
        settingsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(settings: Setting) = with(binding) {
            binding.textViewSettingName.text = settings.nameSetting
            Log.w(itemView.context.packageName, settings.nameSetting)
            if (settings.showSwitch) {
                switchTheme.visible()
            } else {
                switchTheme.gone()
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
