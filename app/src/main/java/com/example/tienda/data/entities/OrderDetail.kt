package com.example.tienda.data.entities

import androidx.room.Entity

@Entity(
    tableName = "order_details",
    primaryKeys = ["orderId", "productId"]
)
data class OrderDetail(
    val orderId: Int,
    val productId: Int,
    val cantidad: Int
)
