package com.example.paggingexample.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paggingexample.data.models.remote.location.SingleLocation
import com.example.paggingexample.databinding.ItemEpisodeBinding
import com.example.paggingexample.databinding.ItemLocationBinding

class LocationsAdapter :
    RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private var characters = listOf<SingleLocation>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<SingleLocation>) {
        characters = list
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: SingleLocation) = with(binding) {
            tvType.text = location.type
            tvName.text = location.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemLocationBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(characters[position])

    }


    override fun getItemCount() = characters.size


}
