package com.example.tienda.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    val nombre: String,
    val precio: Double,
    val categoryId: Int
)
