package com.example.tienda.data.dao

import androidx.room.*
import com.example.tienda.data.entities.OrderDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {
    @Query("SELECT * FROM order_details WHERE orderId = :orderId")
    fun getDetails(orderId: Int): Flow<List<OrderDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderDetail: OrderDetail)
}
