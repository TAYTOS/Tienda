package com.example.tienda.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Customer")
data class Customer(
    @PrimaryKey(autoGenerate = true) val customerId: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String
)
