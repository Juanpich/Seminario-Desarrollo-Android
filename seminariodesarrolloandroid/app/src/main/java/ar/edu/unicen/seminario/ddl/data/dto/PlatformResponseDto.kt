package ar.edu.unicen.seminario.ddl.data.dto

import com.google.gson.annotations.SerializedName
import ar.edu.unicen.seminario.ddl.models.Platform

data class PlatformsResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PlatformDto>
){
    fun toPlataforms():List<Platform>{
        var list = listOf<Platform>()
        results.forEach {
            list +=  Platform (it.id, it.name)
        }
        return list
    }
}
data class PlatformDto(
    val id: Int,
    val name: String,
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String,
    val image: String?,
    @SerializedName("year_start")
    val yearStart: Int?,
    @SerializedName("year_end")
    val yearEnd: Int?
)