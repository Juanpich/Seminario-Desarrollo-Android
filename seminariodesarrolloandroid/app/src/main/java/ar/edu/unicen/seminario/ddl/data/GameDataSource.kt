package ar.edu.unicen.seminario.ddl.data


import android.util.Log
import ar.edu.unicen.seminario.ddl.models.FiltersGame
import ar.edu.unicen.seminario.ddl.models.ItemGames
import ar.edu.unicen.seminario.ui.SortPolicy
import ar.edu.unicen.seminario.ddl.models.Result
import ar.edu.unicen.seminario.ddl.models.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class GameDataSource @Inject constructor(
    private val gamesApi: GamesApi
) {
    suspend fun getListGames(
        key: String,
        genre: Int?,
        platform: Int?,
        store: Int?,
        order: String?
    ): Result<List<ItemGames>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = gamesApi.getAllGames(key, genre, platform, store, order)
                val listGames = response.body()?.toGameList()
                if (listGames != null && listGames.isNotEmpty()) {
                    Result.Success(listGames)
                } else {
                    Result.Error(ErrorType.EMPTY_RESPONSE)
                }
            } catch (e: IOException) {
                Result.Error(ErrorType.NETWORK)
            } catch (e: SocketTimeoutException) {
                Result.Error(ErrorType.TIMEOUT)
            } catch (e: HttpException) {
                Result.Error(ErrorType.SERVER)
            } catch (e: Exception) {
                Result.Error(ErrorType.UNKNOWN)
            }
        }
    }

    suspend fun getAllFilter(key: String): Result<FiltersGame> {
        return withContext(Dispatchers.IO) {
            try {
                val responseGenre = gamesApi.getAllGenres(key)
                val responsePlatform = gamesApi.getAllPlatforms(key)
                val responseStore = gamesApi.getAllStores(key)

                val listGenre = responseGenre.body()?.toGenres()
                val listPlatform = responsePlatform.body()?.toPlataforms()
                val listStore = responseStore.body()?.toStores()

                if (listGenre == null || listPlatform == null || listStore == null) {
                    Result.Error(ErrorType.EMPTY_RESPONSE)
                } else {
                    Result.Success(FiltersGame(listGenre, listPlatform, listStore))
                }
            } catch (e: IOException) {
                Result.Error(ErrorType.NETWORK)
            } catch (e: SocketTimeoutException) {
                Result.Error(ErrorType.TIMEOUT)
            } catch (e: HttpException) {
                Result.Error(ErrorType.SERVER)
            } catch (e: Exception) {
                Result.Error(ErrorType.UNKNOWN)
            }
        }
    }


}

