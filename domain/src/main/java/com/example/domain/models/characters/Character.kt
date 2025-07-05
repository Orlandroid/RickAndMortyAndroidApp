package com.example.domain.models.characters

data class Character(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val urlLocation: String,
    val episode: List<String>,
    val originUrl: String,
) {
    companion object {
        fun mockCharacter() = Character(
            id = 0,
            image = "",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            gender = "Male",
            urlLocation = "https://rickandmortyapi.com/api/location/3",
            episode = listOf("", ""),
            originUrl = "https://rickandmortyapi.com/api/location/3"
        )

        fun getCharacters(howMany: Int): List<Character> {
            val characters = arrayListOf<Character>()
            for (i in 1..howMany) {
                characters.add(mockCharacter())
            }
            return characters
        }
    }

    fun hasNotLocation(): Boolean {
        return urlLocation.isEmpty()
    }
}