package ar.edu.unicen.seminario.ddl.data

import android.util.Log
import ar.edu.unicen.seminario.ddl.models.FiltersGame
import ar.edu.unicen.seminario.ddl.models.ItemGames
import ar.edu.unicen.seminario.ui.SortPolicy
import ar.edu.unicen.seminario.ddl.models.Result

import javax.inject.Inject

class GameRepository @Inject constructor(
    private val gameDataSource: GameDataSource
){
    suspend fun getListGames(
        key: String,
        genreId: Int?,
        platformId: Int?,
        storeId: Int?,
        order: String?
    ): Result<List<ItemGames>> {
        Log.d("Parametros_de_busqueda", "$key, $genreId, $platformId, $storeId, $order")
        return gameDataSource.getListGames(key, genreId, platformId, storeId, order)
    }

    suspend fun getFilters(key: String): Result<FiltersGame> {
        return gameDataSource.getAllFilter(key)
    }

}