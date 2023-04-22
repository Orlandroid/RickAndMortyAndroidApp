package com.rickandmortyorlando.orlando.ui.episodes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmortyorlando.orlando.data.models.remote.episode.Episode
import com.rickandmortyorlando.orlando.databinding.ItemEpisodeBinding
import com.rickandmortyorlando.orlando.ui.extensions.click

class EpisodesAdapter(private val clickOnEpisode: (episodeNumber: Int) -> Unit={}) :
    RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private var episodeList = listOf<Episode>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Episode>) {
        episodeList = list
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) = with(binding) {
            tvEpisode.text = episode.episode
            tvEpisodeName.text = episode.name
            tvAirDate.text = episode.air_date
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemEpisodeBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(episodeList[position])
        viewHolder.itemView.click {
            clickOnEpisode(episodeList[position].id)
        }
    }

    override fun getItemCount() = episodeList.size
}
