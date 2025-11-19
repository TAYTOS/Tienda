package com.example.tienda.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val customerId: Int,
    val orderDate: Date
)
