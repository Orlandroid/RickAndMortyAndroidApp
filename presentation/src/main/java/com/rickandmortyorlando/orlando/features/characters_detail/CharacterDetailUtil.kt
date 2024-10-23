package com.rickandmortyorlando.orlando.features.characters_detail

import com.rickandmortyorlando.orlando.utils.removeCharactersForEpisodesList

fun getListOfEpisodes(episodesString: List<String>): String {
    val episodes = arrayListOf<Int>()
    episodesString.forEach {
        episodes.add(it.split("episode/")[1].toInt())
    }
    return removeCharactersForEpisodesList(episodes.toString())
}