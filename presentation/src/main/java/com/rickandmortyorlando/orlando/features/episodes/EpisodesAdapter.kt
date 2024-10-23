package com.rickandmortyorlando.orlando.features.episodes


import com.example.domain.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.databinding.ItemEpisodeBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickandmortyorlando.orlando.features.extensions.click

class EpisodesAdapter(private val clickOnEpisode: (Episode) -> Unit) :
    PagingDataAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(EpisodeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) = with(binding) {
            tvEpisode.text = episode.episode
            tvEpisodeName.text = episode.name
            tvAirDate.text = episode.air_date
            binding.root.click {
                clickOnEpisode(episode)
            }
        }
    }


    object EpisodeComparator : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }

}