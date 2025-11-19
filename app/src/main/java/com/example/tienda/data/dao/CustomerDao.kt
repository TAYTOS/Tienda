package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: Customer)

    // Read
    @Query("SELECT * FROM Customer")
    fun getAll(): Flow<List<Customer>>

    @Query("SELECT * FROM Customer WHERE customerId = :id")
    suspend fun getById(id: Int): Customer?

    // Update
    @Update
    suspend fun update(customer: Customer)

    // Delete
    @Delete
    suspend fun delete(customer: Customer)
}
