package com.example.tienda.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    val productName: String,
    val price: Double,
    val categoryId: Int
)
