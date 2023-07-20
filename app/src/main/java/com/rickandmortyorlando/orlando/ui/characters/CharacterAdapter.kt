package com.rickandmortyorlando.orlando.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rickandmortyorlando.orlando.R
import com.example.domain.models.remote.character.Character
import com.rickandmortyorlando.orlando.ui.extensions.click
import com.rickandmortyorlando.orlando.utils.getColorStatus
import com.google.android.material.card.MaterialCardView


class CharacterAdapter :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var characters = listOf<Character>()
    private var clickOnCharacter: ClickOnCharacter? = null

    fun setData(list: List<Character>) {
        characters = list.distinct()
        notifyDataSetChanged()
    }

    fun setListener(clickOnCharacter: ClickOnCharacter) {
        this.clickOnCharacter = clickOnCharacter
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(character: Character) {
            val image = view.findViewById<ImageView>(R.id.imageView)
            val imageStatus = view.findViewById<ImageView>(R.id.image_status_session)
            val name = view.findViewById<TextView>(R.id.tv_character_name)
            val status = view.findViewById<TextView>(R.id.tv_status)
            val specie = view.findViewById<TextView>(R.id.tv_specie)
            val card = view.findViewById<MaterialCardView>(R.id.card)
            name.text = character.name
            Glide.with(itemView.context).load(character.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.loading_animation).into(image)
            imageStatus.setColorFilter(getColorStatus(character.status, itemView.context))
            card.strokeColor = getColorStatus(character.status, itemView.context)
            status.text = character.status
            specie.text = character.species
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_character, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(characters[position])
        viewHolder.itemView.click {
            clickOnCharacter?.clickOnCharacter(characters[position])
        }
    }


    override fun getItemCount() = characters.size

    interface ClickOnCharacter {
        fun clickOnCharacter(character: Character)
    }

}
