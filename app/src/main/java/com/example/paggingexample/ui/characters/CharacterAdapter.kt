package com.example.paggingexample.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paggingexample.R
import com.example.paggingexample.data.models.Character
import com.example.paggingexample.utils.getColorStatus


class CharacterAdapter :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var characters = listOf<Character>()

    fun setData(list: List<Character>) {
        characters = list
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(character: Character) {
            val image = view.findViewById<ImageView>(R.id.imageView)
            val imageStatus = view.findViewById<ImageView>(R.id.image_status_session)
            val name = view.findViewById<TextView>(R.id.tv_character_name)
            val status = view.findViewById<TextView>(R.id.tv_status)
            val specie = view.findViewById<TextView>(R.id.tv_specie)
            name.text = character.name
            Glide.with(itemView.context).load(character.image).transition(DrawableTransitionOptions.withCrossFade()).into(image)
            imageStatus.setColorFilter(getColorStatus(character.status, itemView.context))
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
    }


    override fun getItemCount() = characters.size

}
