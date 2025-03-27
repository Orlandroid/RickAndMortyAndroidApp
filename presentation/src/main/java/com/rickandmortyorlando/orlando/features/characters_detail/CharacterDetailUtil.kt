package com.rickandmortyorlando.orlando.features.characters_detail

import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import com.rickandmortyorlando.orlando.utils.removeCharactersForEpisodesList

fun getListOfEpisodes(episodesString: List<String>): String {
    val episodes = arrayListOf<Int>()
    episodesString.forEach {
        episodes.add(it.split("episode/")[1].toInt())
    }
    return removeCharactersForEpisodesList(episodes.toString())
}

fun characterHasLocation(urlLocation: String): Boolean {
    return urlLocation.isNotEmpty()
}

fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
    return getListOfNumbersFromUrlWithPrefix(
        idsInUrl,
        "character"
    )
}