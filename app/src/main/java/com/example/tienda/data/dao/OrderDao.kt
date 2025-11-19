package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: Order)

    // Read
    @Query("SELECT * FROM Orders")
    fun getAll(): Flow<List<Order>>

    @Query("SELECT * FROM Orders WHERE orderId = :id")
    suspend fun getById(id: Int): Order?

    // Update
    @Update
    suspend fun update(order: Order)

    // Delete
    @Delete
    suspend fun delete(order: Order)
}
