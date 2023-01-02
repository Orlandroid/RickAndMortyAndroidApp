package com.example.paggingexample.ui.episodes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paggingexample.data.models.remote.location.episode.Episode
import com.example.paggingexample.databinding.ItemEpisodeBinding

class EpisodesAdapter :
    RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private var characters = listOf<Episode>()


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Episode>) {
        characters = list
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
        val binding = ItemEpisodeBinding.inflate(layoutInflater,viewGroup,false)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(characters[position])

    }


    override fun getItemCount() = characters.size


}
