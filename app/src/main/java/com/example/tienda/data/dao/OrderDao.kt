package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)
}
