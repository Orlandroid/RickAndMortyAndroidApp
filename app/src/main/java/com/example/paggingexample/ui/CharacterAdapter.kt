package com.example.paggingexample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paggingexample.R
import com.example.paggingexample.data.models.Character


class CharacterAdapter :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var characters= listOf<Character>()

     fun setData(list: List<Character>){
        characters=list
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(character: Character) {
            val image=view.findViewById<ImageView>(R.id.imageView)
            val name=view.findViewById<TextView>(R.id.tv_character_name)
            name.text=character.name
            Glide.with(itemView.context).load(character.image).into(image)
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
