package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.OrderDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderDetail: OrderDetail)

    // Read
    @Query("SELECT * FROM OrderDetail WHERE orderId = :orderId")
    fun getDetails(orderId: Int): Flow<List<OrderDetail>>

    @Query("""
        SELECT * FROM OrderDetail 
        WHERE orderId = :orderId AND productId = :productId
    """)
    suspend fun getDetail(orderId: Int, productId: Int): OrderDetail?

    // Update
    @Update
    suspend fun update(orderDetail: OrderDetail)

    // Delete
    @Delete
    suspend fun delete(orderDetail: OrderDetail)

    @Query("DELETE FROM OrderDetail WHERE orderId = :orderId")
    suspend fun deleteAllFromOrder(orderId: Int)
}
