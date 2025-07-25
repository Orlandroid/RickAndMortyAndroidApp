package com.example.domain.utils


fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
    return getListOfNumbersFromUrlWithPrefix(
        idsInUrl,
        "character"
    )
}

fun removeCharactersForEpisodesList(episodesList: String): String {
    return episodesList.replace("[", "").replace("]", "").replace(" ", "")
}

fun getListOfNumbersFromUrlWithPrefix(
    episodesString: List<String>,
    prefix: String
): String {
    val episodes = arrayListOf<Int>()
    episodesString.forEach {
        episodes.add(it.split("$prefix/")[1].toInt())
    }
    return removeCharactersForEpisodesList(episodes.toString())
}

fun getNumberFromUrWithPrefix(urlWithNumberInTheFinalCharacter: String, prefix: String): Int {
    return urlWithNumberInTheFinalCharacter.split("$prefix/")[1].toInt()
}

fun getNumberOfLocationFromUrl(locationUrl: String): Int {
    return locationUrl.split("location/")[1].toInt()
}

fun String.isSingleCharacter() = !this.contains(",")

fun getListOfEpisodes(episodesString: List<String>): String {
    val episodes = arrayListOf<Int>()
    episodesString.forEach {
        episodes.add(it.split("episode/")[1].toInt())
    }
    return removeCharactersForEpisodesList(episodes.toString())
}