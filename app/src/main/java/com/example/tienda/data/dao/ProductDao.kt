package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    // Read
    @Query("SELECT * FROM Product")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE categoryId = :id")
    fun getByCategory(id: Int): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE productId = :id")
    suspend fun getById(id: Int): Product?

    // Update
    @Update
    suspend fun update(product: Product)

    // Delete
    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM Product WHERE categoryId = :id")
    suspend fun deleteByCategory(id: Int)
}
