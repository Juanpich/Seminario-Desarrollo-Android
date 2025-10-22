package ar.edu.unicen.seminario.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.edu.unicen.seminario.R
import ar.edu.unicen.seminario.databinding.ActivityListgamesBinding
import ar.edu.unicen.seminario.ddl.models.ErrorType
import ar.edu.unicen.seminario.ddl.models.FilterHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ListGamesActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListGamesViewModel>()
    private lateinit var binding: ActivityListgamesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityListgamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMenu.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }

        viewModel.loading.onEach { loading ->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
                binding.btnCargar.isEnabled = false
            }else{
                binding.progressBar.visibility = View.INVISIBLE
                binding.btnCargar.isEnabled = true
            }
        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if (error){
                binding.textPrueba.text = "Error al cargar los datos"
                binding.recyclerViewGames.visibility =View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }else {
                binding.textPrueba.text = "Lista con datos"
                binding.recyclerViewGames.visibility = View.VISIBLE
            }

        }.launchIn(lifecycleScope)


        lifecycleScope.launchWhenStarted {
            viewModel.games.collect { games ->
                if (games?.isNotEmpty() == true) {
                    val adapter = GamesAdapter(games)
                    binding.recyclerViewGames.layoutManager = LinearLayoutManager(binding.root.context)
                    binding.recyclerViewGames.adapter = adapter
                }
            }
        }

        binding.btnCargar.setOnClickListener {
                viewModel.getListGames("8151c22125e04851ba85b008395c992f")
        }
        viewModel.errorType.onEach { error ->
            if (error != null) {
                binding.errorMessage.text = when (error) {
                    ErrorType.NETWORK -> "Error de conexión. Verificá tu internet."
                    ErrorType.TIMEOUT -> "Tiempo de espera agotado. Intentá de nuevo."
                    ErrorType.SERVER -> "Error del servidor. Intenta más tarde."
                    ErrorType.EMPTY_RESPONSE -> "No se encontraron juegos."
                    ErrorType.UNKNOWN -> "Error inesperado."
                }
                binding.errorMessage.visibility = View.VISIBLE
            } else {
                binding.errorMessage.visibility = View.GONE
            }
        }.launchIn(lifecycleScope)

    }
    override fun onResume() {
        super.onResume()
        FilterHolder.filters?.let { newFilters ->
            val current = viewModel.currentFilters
            if (current != newFilters) {
                viewModel.applyFilters(
                    genreId = newFilters.genreId,
                    platformId = newFilters.platformId,
                    storeId = newFilters.storeId,
                    sortPolicy = newFilters.sortPolicy
                )
                viewModel.getListGames("8151c22125e04851ba85b008395c992f")
            }
        }
    }





}