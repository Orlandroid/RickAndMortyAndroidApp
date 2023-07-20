package com.rickandmortyorlando.orlando.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.databinding.ItemLocationBinding
import com.rickandmortyorlando.orlando.ui.extensions.click

class LocationsAdapter(private val clickOnLocation: (locationId: Int) -> Unit = {}) :
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
        val currentLocation = characters[position]
        viewHolder.bind(characters[position])
        viewHolder.itemView.click {
            clickOnLocation(currentLocation.id)
        }
    }


    override fun getItemCount() = characters.size


}
