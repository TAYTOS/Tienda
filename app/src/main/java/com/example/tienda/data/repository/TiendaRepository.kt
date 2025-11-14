package com.example.tienda.data.repository

import com.example.tienda.data.dao.*
import com.example.tienda.data.entities.*
import kotlinx.coroutines.flow.Flow

class TiendaRepository(
    private val customerDao: CustomerDao,
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao,
    private val orderDao: OrderDao,
    private val orderDetailDao: OrderDetailDao
) {
    fun getCustomers(): Flow<List<Customer>> = customerDao.getAll()
    suspend fun addCustomer(c: Customer) = customerDao.insert(c)

    fun getCategories(): Flow<List<Category>> = categoryDao.getAll()
    suspend fun addCategory(c: Category) = categoryDao.insert(c)

    fun getProducts(): Flow<List<Product>> = productDao.getAll()
    suspend fun addProduct(p: Product) = productDao.insert(p)

    fun getOrders(): Flow<List<Order>> = orderDao.getAll()
    suspend fun addOrder(o: Order) = orderDao.insert(o)

    fun getOrderDetails(id: Int): Flow<List<OrderDetail>> =
        orderDetailDao.getDetails(id)

    suspend fun addOrderDetail(d: OrderDetail) =
        orderDetailDao.insert(d)
}
