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
    // ========== CUSTOMERS ==========
    fun getCustomers(): Flow<List<Customer>> = customerDao.getAll()
    suspend fun getCustomerById(id: Int): Customer? = customerDao.getById(id)
    suspend fun addCustomer(c: Customer) = customerDao.insert(c)
    suspend fun updateCustomer(c: Customer) = customerDao.update(c)
    suspend fun deleteCustomer(c: Customer) = customerDao.delete(c)

    // ========== CATEGORIES ==========
    fun getCategories(): Flow<List<Category>> = categoryDao.getAll()
    suspend fun getCategoryById(id: Int): Category? = categoryDao.getById(id)
    suspend fun addCategory(c: Category) = categoryDao.insert(c)
    suspend fun updateCategory(c: Category) = categoryDao.update(c)
    suspend fun deleteCategory(c: Category) = categoryDao.delete(c)
    suspend fun deleteAllCategories() = categoryDao.deleteAll()

    // ========== PRODUCTS ==========
    fun getProducts(): Flow<List<Product>> = productDao.getAll()
    suspend fun getProductById(id: Int): Product? = productDao.getById(id)
    // Consulta 1-n: Productos por categoría
    fun getProductsByCategory(categoryId: Int): Flow<List<Product>> =
        productDao.getByCategory(categoryId)
    suspend fun addProduct(p: Product) = productDao.insert(p)
    suspend fun updateProduct(p: Product) = productDao.update(p)
    suspend fun deleteProduct(p: Product) = productDao.delete(p)

    // ========== ORDERS ==========
    fun getOrders(): Flow<List<Order>> = orderDao.getAll()
    suspend fun getOrderById(id: Int): Order? = orderDao.getById(id)
    suspend fun addOrder(o: Order) = orderDao.insert(o)
    suspend fun updateOrder(o: Order) = orderDao.update(o)
    suspend fun deleteOrder(o: Order) = orderDao.delete(o)

    // ========== ORDER DETAILS ==========
    // Consulta n-m: Detalles de una orden (relaciona Order con Product)
    fun getOrderDetails(orderId: Int): Flow<List<OrderDetail>> =
        orderDetailDao.getDetails(orderId)
    suspend fun getOrderDetail(orderId: Int, productId: Int): OrderDetail? =
        orderDetailDao.getDetail(orderId, productId)
    suspend fun addOrderDetail(d: OrderDetail) = orderDetailDao.insert(d)
    suspend fun updateOrderDetail(d: OrderDetail) = orderDetailDao.update(d)
    suspend fun deleteOrderDetail(d: OrderDetail) = orderDetailDao.delete(d)
    suspend fun deleteAllOrderDetails(orderId: Int) =
        orderDetailDao.deleteAllFromOrder(orderId)
}
