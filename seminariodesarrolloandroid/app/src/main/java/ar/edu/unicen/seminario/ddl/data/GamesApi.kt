package ar.edu.unicen.seminario.ddl.data

import ar.edu.unicen.seminario.ddl.data.dto.GamesResponseDto
import ar.edu.unicen.seminario.ddl.data.dto.GenresResponseDto
import ar.edu.unicen.seminario.ddl.data.dto.PlatformsResponseDto
import ar.edu.unicen.seminario.ddl.data.dto.StoresResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {
    @GET("games")
    suspend fun getAllGames(
        @Query("key") key: String,
        @Query("genres") genre: Int? = null,
        @Query("platforms") platform: Int? = null,
        @Query("stores") store: Int? = null,
        @Query("ordering") ordering: String? = null

    ): Response<GamesResponseDto>
    @GET("stores")
    suspend fun getAllStores(
        @Query("key") key: String,
    ): Response<StoresResponseDto>
    @GET("platforms")
    suspend fun getAllPlatforms(
        @Query("key") key: String,
    ): Response<PlatformsResponseDto>
    @GET("genres")
    suspend fun getAllGenres(
        @Query("key") key: String,
    ): Response<GenresResponseDto>





}