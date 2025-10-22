package ar.edu.unicen.seminario.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.data.GameRepository
import ar.edu.unicen.seminario.ddl.models.ErrorType
import ar.edu.unicen.seminario.ddl.models.Result
import ar.edu.unicen.seminario.ddl.models.FilterParams
import ar.edu.unicen.seminario.ddl.models.ItemGames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListGamesViewModel @Inject constructor(
    private val repository: GameRepository
): ViewModel() {
    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val _errorType = MutableStateFlow<ErrorType?>(null)
    val errorType: StateFlow<ErrorType?> = _errorType

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _games = MutableStateFlow<List<ItemGames>>(emptyList())
    val games: StateFlow<List<ItemGames>> = _games


    private val _genreFilter = MutableStateFlow<Int?>(null)
    private val _platformFilter = MutableStateFlow<Int?>(null)
    private val _storeFilter = MutableStateFlow<Int?>(null)
    private val _sortPolicy = MutableStateFlow<SortPolicy?>(null)
    var currentFilters = FilterParams()

    fun getListGames(key: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _games.value = emptyList()

            val genreId = _genreFilter.value
            val platformId = _platformFilter.value
            val storeId = _storeFilter.value
            val sortPolicy = _sortPolicy.value

            when (val result = repository.getListGames(
                key = key,
                genreId = genreId,
                platformId = platformId,
                storeId = storeId,
                order = sortPolicy?.column
            )) {
                is Result.Success<List<ItemGames>> -> {
                    _games.value = result.data
                    _error.value = false
                    _errorType.value = null
                }
                is Result.Error -> {
                    _games.value = emptyList()
                    _error.value = true
                    _errorType.value = result.type
                }
            }

            _loading.value = false
        }
    }
    fun applyFilters(
        genreId: Int?,
        platformId: Int?,
        storeId: Int?,
        sortPolicy: SortPolicy?
    ) {
        currentFilters = FilterParams(genreId, platformId, storeId, sortPolicy)
        _genreFilter.value = genreId
        _platformFilter.value = platformId
        _storeFilter.value = storeId
        _sortPolicy.value = sortPolicy
    }








}