package ar.edu.unicen.seminario.ddl.data.dto

import ar.edu.unicen.seminario.ddl.models.Store
import com.google.gson.annotations.SerializedName

data class StoresResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<StoreDto>
){
    fun toStores():List<Store>{
        var list = listOf<Store>()
        results.forEach {
            list += Store(it.id, it.name)
        }
        return list
    }
}
data class StoreDto(
    val id: Int,
    val name: String,
    val domain: String,
    val slug: String,
    @SerializedName("games_count")
    val gamesCount: Int,
    @SerializedName("image_background")
    val imageBackground: String
)