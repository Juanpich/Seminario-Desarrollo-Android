package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.databinding.ActivityFilterBinding
import ar.edu.unicen.seminario.ddl.models.ErrorType
import ar.edu.unicen.seminario.ddl.models.FilterHolder
import ar.edu.unicen.seminario.ddl.models.FilterParams
import ar.edu.unicen.seminario.ddl.models.Genre
import ar.edu.unicen.seminario.ddl.models.Platform
import ar.edu.unicen.seminario.ddl.models.Store
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.getValue


@AndroidEntryPoint
class FilterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding
    private val viewModel by viewModels<FilterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var selectedGenre: Genre? = null
        var selectedPlatform: Platform? = null
        var selectedStore: Store? = null
        var selectedSortPolicy: SortPolicy? = null

        binding.returnMenu.setOnClickListener {
            val intent = Intent(this, ListGamesActivity::class.java)
            FilterHolder.filters = FilterParams(//filtros que voy a aplicar
                genreId = selectedGenre?.id,
                platformId = selectedPlatform?.id,
                storeId = selectedStore?.id,
                sortPolicy = selectedSortPolicy
            )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.clearFilter.setOnClickListener {
            binding.spinnerGenre.setSelection(0)
            binding.spinnerPlatform.setSelection(0)
            binding.spinnerStore.setSelection(0)
            binding.spinnerOrder.setSelection(0)
            selectedGenre = null
            selectedPlatform = null
            selectedStore = null
            selectedSortPolicy = null
            FilterHolder.filters = null

        }
        viewModel.error.onEach { hasError ->
            binding.errorMessage.visibility = if (hasError) View.VISIBLE else View.GONE
            binding.retryBtn.visibility = if (hasError) View.VISIBLE else View.GONE
        }.launchIn(lifecycleScope)

        viewModel.errorType.onEach { type ->
            binding.errorMessage.text = when (type) {
                ErrorType.NETWORK -> "Error de conexión. Verificá tu internet."
                ErrorType.TIMEOUT -> "Tiempo de espera agotado. Intentá de nuevo."
                ErrorType.SERVER -> "Error del servidor. Intenta más tarde."
                ErrorType.EMPTY_RESPONSE -> "No se encontraron filtros."
                ErrorType.UNKNOWN -> "Error inesperado."
                null -> ""
            }
        }.launchIn(lifecycleScope)

        binding.retryBtn.setOnClickListener {
            viewModel.getFilters("8151c22125e04851ba85b008395c992f")
        }





        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
        lifecycleScope.launchWhenStarted {
            viewModel.filter.collect { filtersGame ->
                filtersGame?.let {
                    val genreItems = listOf(Genre(-1, "Seleccionar género")) + (it.genres ?: emptyList())
                    val platformItems = listOf(Platform(-1, "Seleccionar plataforma")) + (it.platforms ?: emptyList())
                    val storeItems = listOf(Store(-1, "Seleccionar tienda")) + (it.stores ?: emptyList())
                    val sortOptions = listOf("Seleccionar orden", "Nombre", "Rating", "Lanzamiento")
                    val initialFilters = FilterHolder.filters

                    setupSpinner(binding.spinnerGenre, genreItems, { it.name }, { it.id == -1 }, {
                        selectedGenre = it
                    }, initialValue = genreItems.find { it.id == initialFilters?.genreId })

                    setupSpinner(binding.spinnerPlatform, platformItems, { it.name }, { it.id == -1 }, {
                        selectedPlatform = it
                    }, initialValue = platformItems.find { it.id == initialFilters?.platformId })

                    setupSpinner(binding.spinnerStore, storeItems, { it.name }, { it.id == -1 }, {
                        selectedStore = it
                    }, initialValue = storeItems.find { it.id == initialFilters?.storeId })

                    setupSpinner(binding.spinnerOrder, sortOptions, { it }, { it == "Seleccionar orden" }, { selectedLabel ->
                        selectedSortPolicy = SortPolicy.fromLabel(selectedLabel)
                    }, initialValue = initialFilters?.sortPolicy?.label)


                }
            }
        }
        viewModel.getFilters("8151c22125e04851ba85b008395c992f")
    }
    fun <T> setupSpinner(
        spinner: Spinner,
        items: List<T>,
        getLabel: (T) -> String,
        isPlaceholder: (T) -> Boolean,
        onSelected: ((T) -> Unit)? = null,
        initialValue: T? = null
    ) {
        val labels = items.map(getLabel)
        val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, labels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        initialValue?.let {
            val index = items.indexOf(it)
            if (index >= 0) spinner.setSelection(index)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = items[position]
                if (!isPlaceholder(selected)) {
                    onSelected?.invoke(selected)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }




}

