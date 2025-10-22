package ar.edu.unicen.seminario.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminario.ddl.data.GameRepository
import ar.edu.unicen.seminario.ddl.models.ErrorType
import ar.edu.unicen.seminario.ddl.models.Result
import ar.edu.unicen.seminario.ddl.models.FiltersGame
import ar.edu.unicen.seminario.ddl.models.ItemGames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()

    private val _errorType = MutableStateFlow<ErrorType?>(null)
    val errorType = _errorType.asStateFlow()

    private val _filter = MutableStateFlow<FiltersGame?>(null)
    val filter = _filter.asStateFlow()

    fun getFilters(key: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            _errorType.value = null
            _filter.value = null

            when (val result = repository.getFilters(key)) {
                is Result.Success -> {
                    _filter.value = result.data
                    _error.value = false
                    _errorType.value = null
                }
                is Result.Error -> {
                    _filter.value = null
                    _error.value = true
                    _errorType.value = result.type
                }
            }

            _loading.value = false
        }
    }
}