package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    // Read
    @Query("SELECT * FROM Category")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM Category WHERE categoryId = :id")
    suspend fun getById(id: Int): Category?

    // Update
    @Update
    suspend fun update(category: Category)

    // Delete
    @Delete
    suspend fun delete(category: Category)

    @Query("DELETE FROM Category")
    suspend fun deleteAll()
}
