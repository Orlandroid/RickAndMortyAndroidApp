package com.rickandmortyorlando.orlando.features.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.remote.location.SingleLocation
import com.rickandmortyorlando.orlando.databinding.ItemLocationBinding
import com.rickandmortyorlando.orlando.features.extensions.click

class LocationsAdapter(private val clickOnLocation: (locationId: Int) -> Unit = {}) :
    PagingDataAdapter<SingleLocation, LocationsAdapter.LocationViewHolder>(LocationComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): LocationViewHolder {
        return LocationViewHolder(
            ItemLocationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationsAdapter.LocationViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class LocationViewHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: SingleLocation) = with(binding) {
            root.click { clickOnLocation(location.id) }
            tvType.text = location.type
            tvName.text = location.name
        }
    }

    object LocationComparator : DiffUtil.ItemCallback<SingleLocation>() {
        override fun areItemsTheSame(oldItem: SingleLocation, newItem: SingleLocation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SingleLocation, newItem: SingleLocation): Boolean {
            return oldItem == newItem
        }
    }

}
