package com.example.tienda.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true) val customerId: Int = 0,
    val nombre: String,
    val direccion: String,
    val telefono: String
)
