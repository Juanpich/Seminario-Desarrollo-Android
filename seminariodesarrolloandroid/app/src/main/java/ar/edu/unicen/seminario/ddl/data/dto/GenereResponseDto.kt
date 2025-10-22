package ar.edu.unicen.seminario.ddl.data.dto

import ar.edu.unicen.seminario.ddl.models.Genre
import com.google.gson.annotations.SerializedName

data class GenresResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GenreDto>
){
    fun toGenres(): List<Genre> {
        var list = listOf<Genre>()
        results.forEach {
            list += Genre(it.id, it.name)
        }
        return list
    }
}
data class GenreDto(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)
