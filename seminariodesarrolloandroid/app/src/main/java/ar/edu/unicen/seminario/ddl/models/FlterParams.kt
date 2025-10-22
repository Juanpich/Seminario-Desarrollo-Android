package ar.edu.unicen.seminario.ddl.models

import ar.edu.unicen.seminario.ui.SortPolicy

data class FilterParams(
    val genreId: Int? = null,
    val platformId: Int? = null,
    val storeId: Int? = null,
    val sortPolicy: SortPolicy? = null
)
