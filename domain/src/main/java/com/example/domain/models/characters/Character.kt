package com.example.domain.models.characters

data class Character(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val urlLocation: String,
    val episode: List<String>
) {
    companion object {
        fun emptyCharacter() = Character(
            id = 0,
            image = "",
            name = "",
            status = "",
            species = "",
            gender = "",
            urlLocation = "",
            episode = emptyList()
        )

        fun getCharacters(howMany: Int): List<Character> {
            val characters = arrayListOf<Character>()
            for (i in 1..howMany) {
                characters.add(emptyCharacter())
            }
            return characters
        }
    }
}