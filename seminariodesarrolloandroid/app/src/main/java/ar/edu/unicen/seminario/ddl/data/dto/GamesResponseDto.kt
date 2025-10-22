package ar.edu.unicen.seminario.ddl.data.dto

import ar.edu.unicen.seminario.ddl.models.ItemGames
import com.google.gson.annotations.SerializedName

data class GamesResponseDto(
        @SerializedName("count")
        val count: Int,
        @SerializedName("next")
        val next: String?,
        @SerializedName("previous")
        val previous: String?,
        @SerializedName("results")
        val results: List<Game>
    ){
    fun toGameList(): List<ItemGames> {
        var games:List<ItemGames> = listOf()
        for (game in results) {
            var itemGame = ItemGames(game.id,game.name,game.released, game.rating, game.background_image )
            games += itemGame
        }
        return games
    }
}

    data class Game(
        @SerializedName("id")
        val id: Int,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("released")
        val released: String?,
        @SerializedName("tba")
        val tba: Boolean,
        @SerializedName("background_image")
        val background_image: String?,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("rating_top")
        val rating_top: Double,
        @SerializedName("ratings")
        val ratings: List<Rating>?,
        @SerializedName("ratings_count")
        val ratings_count: Int,
        @SerializedName("reviews_text_count")
        val reviews_text_count: String?,
        @SerializedName("added")
        val added: Int,
        @SerializedName("added_by_status")
        val added_by_status: Map<String, Any>?,
        @SerializedName("metacritic")
        val metacritic: Int?,
        @SerializedName("playtime")
        val playtime: Int,
        @SerializedName("suggestions_count")
        val suggestions_count: Int,
        @SerializedName("updated")
        val updated: String?,
        @SerializedName("user_game")
        val user_game: String?,
        @SerializedName("esrb_rating")
        val esrb_rating: EsrbRating?,
        @SerializedName("platforms")
        val platforms: List<PlatformInfo>?
    )

    data class EsrbRating(
        @SerializedName("id")
        val id: Int,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("name")
        val name: String
    )

    data class PlatformInfo(
        @SerializedName("platform")
        val platform: Platform,
        @SerializedName("released_at")
        val released_at: String?,
        @SerializedName("requirements_en")
        val requirements: Requirements?
    )

    data class Platform(
        @SerializedName("id")
        val id: Int,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("name")
        val name: String
    )

    data class Requirements(
        @SerializedName("minimum")
        val minimum: String?,
        @SerializedName("recommended")
        val recommended: String?
    )
data class Rating(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("percent")
    val percent: Double
)
