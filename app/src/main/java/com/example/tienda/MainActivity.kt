package com.example.tienda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.tienda.data.DataLoader
import com.example.tienda.data.db.AppDatabase
import com.example.tienda.data.repository.TiendaRepository
import com.example.tienda.ui.screens.MainScreen
import com.example.tienda.ui.theme.TiendaTheme
import com.example.tienda.viewmodel.TiendaViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Iniciando la BD
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tienda_database"
        ).build()

        // Crear repositorio
        val repository = TiendaRepository(
            db.customerDao(),
            db.categoryDao(),
            db.productDao(),
            db.orderDao(),
            db.orderDetailDao()
        )

        // Crear el ViewModel
        val viewModel = TiendaViewModel(repository)

        // Cargar datos iniciales
        loadInitialData(viewModel)

        setContent {
            TiendaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }

    private fun loadInitialData(viewModel: TiendaViewModel) {
        lifecycleScope.launch {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("is_first_run", true)

            if (isFirstRun) {
                val dataLoader = DataLoader(applicationContext)

                viewModel.loadInitialData(
                    categories = dataLoader.loadCategories(),
                    products = dataLoader.loadProducts(),
                    customers = dataLoader.loadCustomers()
                )

                prefs.edit { putBoolean("is_first_run", false) }
            }
        }
    }
}