package com.rickandmortyorlando.orlando.features.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.domain.models.characters.Character
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.ItemCharacterBinding
import com.rickandmortyorlando.orlando.features.extensions.click
import com.rickandmortyorlando.orlando.utils.getColorStatus


class CharacterAdapter(private val clickOnCharacter: (Character) -> Unit) :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterComparator) {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) = with(binding) {
            tvCharacterName.text = character.name
            Glide.with(itemView.context).load(character.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.loading_animation).into(imageCharacter)
            imageStatusSession.setColorFilter(getColorStatus(character.status, itemView.context))
            card.strokeColor = getColorStatus(character.status, itemView.context)
            tvStatus.text = character.status
            tvSpecie.text = character.species
            root.click {
                clickOnCharacter(character)
            }
        }
    }

    object CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

}
