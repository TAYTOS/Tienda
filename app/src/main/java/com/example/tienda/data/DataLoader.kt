package com.example.tienda.data

import android.content.Context
import com.example.tienda.data.entities.Category
import com.example.tienda.data.entities.Customer
import com.example.tienda.data.entities.Product
import org.json.JSONArray
import org.json.JSONObject

class DataLoader(private val context: Context) {

    fun loadCategories(): List<Category> {
        return try {
            val json = context.assets.open("categories.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)
            List(jsonArray.length()) { i ->
                val obj = jsonArray.getJSONObject(i)
                Category(
                    name = obj.getString("name")
                )
            }
        } catch (e: Exception) {
            // Si no existe el archivo, retornar datos por defecto
            getDefaultCategories()
        }
    }

    fun loadProducts(): List<Product> {
        return try {
            val json = context.assets.open("products.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)
            List(jsonArray.length()) { i ->
                val obj = jsonArray.getJSONObject(i)
                Product(
                    productName = obj.getString("name"),
                    price = obj.getDouble("price"),
                    categoryId = obj.getInt("categoryId")
                )
            }
        } catch (e: Exception) {
            getDefaultProducts()
        }
    }

    fun loadCustomers(): List<Customer> {
        return try {
            val json = context.assets.open("customers.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)
            List(jsonArray.length()) { i ->
                val obj = jsonArray.getJSONObject(i)
                Customer(
                    firstName = obj.getString("firstName"),
                    lastName = obj.getString("lastName"),
                    email = obj.getString("email")
                )
            }
        } catch (e: Exception) {
            getDefaultCustomers()
        }
    }

    // Datos por defecto para una bodega
    private fun getDefaultCategories() = listOf(
        Category(name = "Bebidas"),
        Category(name = "Abarrotes"),
        Category(name = "Lácteos"),
        Category(name = "Snacks"),
        Category(name = "Limpieza"),
        Category(name = "Panadería")
    )

    private fun getDefaultProducts() = listOf(
        // Bebidas
        Product(productName = "Coca Cola 1.5L", price = 4.50, categoryId = 1),
        Product(productName = "Inca Kola 2L", price = 5.00, categoryId = 1),
        Product(productName = "Agua San Luis 625ml", price = 1.50, categoryId = 1),
        Product(productName = "Cerveza Pilsen 330ml", price = 3.50, categoryId = 1),

        // Abarrotes
        Product(productName = "Arroz Costeño 1kg", price = 4.20, categoryId = 2),
        Product(productName = "Azúcar Blanca 1kg", price = 3.80, categoryId = 2),
        Product(productName = "Aceite Primor 1L", price = 8.50, categoryId = 2),
        Product(productName = "Fideo Don Vittorio 250g", price = 2.00, categoryId = 2),

        // Lácteos
        Product(productName = "Leche Gloria 1L", price = 4.50, categoryId = 3),
        Product(productName = "Yogurt Gloria 1L", price = 6.00, categoryId = 3),
        Product(productName = "Queso Fresco 500g", price = 12.00, categoryId = 3),
        Product(productName = "Mantequilla Gloria 200g", price = 7.50, categoryId = 3),

        // Snacks
        Product(productName = "Papas Lays 150g", price = 5.50, categoryId = 4),
        Product(productName = "Doritos 140g", price = 5.00, categoryId = 4),
        Product(productName = "Galletas Oreo 432g", price = 8.90, categoryId = 4),
        Product(productName = "Chocolate Sublime 30g", price = 2.00, categoryId = 4),

        // Limpieza
        Product(productName = "Detergente Ariel 900g", price = 12.50, categoryId = 5),
        Product(productName = "Lejía Clorox 1L", price = 4.50, categoryId = 5),
        Product(productName = "Papel Higiénico Elite 4un", price = 6.50, categoryId = 5),
        Product(productName = "Jabón Bolívar 3un", price = 3.00, categoryId = 5),

        // Panadería
        Product(productName = "Pan Francés", price = 0.30, categoryId = 6),
        Product(productName = "Pan Integral", price = 0.50, categoryId = 6),
        Product(productName = "Tostadas Bimbo", price = 5.50, categoryId = 6),
        Product(productName = "Pan de Molde Bimbo", price = 6.00, categoryId = 6)
    )

    private fun getDefaultCustomers() = listOf(
        Customer(firstName = "Juan", lastName = "Pérez", email = "juan.perez@email.com"),
        Customer(firstName = "María", lastName = "García", email = "maria.garcia@email.com"),
        Customer(firstName = "Carlos", lastName = "López", email = "carlos.lopez@email.com"),
        Customer(firstName = "Ana", lastName = "Martínez", email = "ana.martinez@email.com"),
        Customer(firstName = "Luis", lastName = "Rodríguez", email = "luis.rodriguez@email.com")
    )
}

