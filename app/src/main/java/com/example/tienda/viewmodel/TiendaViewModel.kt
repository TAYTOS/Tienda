package com.example.tienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tienda.data.entities.*
import com.example.tienda.data.repository.TiendaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class TiendaViewModel(private val repository: TiendaRepository) : ViewModel() {

    // Estados
    val categories = repository.getCategories().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val products = repository.getProducts().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val customers = repository.getCustomers().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val orders = repository.getOrders().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // Estado de productos filtrados por categoría
    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    val filteredProducts = _selectedCategoryId.flatMapLatest { categoryId ->
        if (categoryId != null) {
            repository.getProductsByCategory(categoryId)
        } else {
            repository.getProducts()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Estado de detalles de orden seleccionada
    private val _selectedOrderId = MutableStateFlow<Int?>(null)
    val selectedOrderId = _selectedOrderId.asStateFlow()

    val orderDetails = _selectedOrderId.flatMapLatest { orderId ->
        if (orderId != null) {
            repository.getOrderDetails(orderId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // CATEGORÍAS
    fun addCategory(name: String) = viewModelScope.launch {
        repository.addCategory(Category(name = name))
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        repository.updateCategory(category)
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        repository.deleteCategory(category)
    }

    fun selectCategory(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
    }

    // PRODUCTOS
    fun addProduct(name: String, price: Double, categoryId: Int) = viewModelScope.launch {
        repository.addProduct(Product(productName = name, price = price, categoryId = categoryId))
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.updateProduct(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    // CLIENTES
    fun addCustomer(firstName: String, lastName: String, email: String) = viewModelScope.launch {
        repository.addCustomer(Customer(firstName = firstName, lastName = lastName, email = email))
    }

    fun updateCustomer(customer: Customer) = viewModelScope.launch {
        repository.updateCustomer(customer)
    }

    fun deleteCustomer(customer: Customer) = viewModelScope.launch {
        repository.deleteCustomer(customer)
    }

    // ÓRDENES
    fun addOrder(customerId: Int) = viewModelScope.launch {
        repository.addOrder(Order(customerId = customerId, orderDate = Date()))
    }

    fun updateOrder(order: Order) = viewModelScope.launch {
        repository.updateOrder(order)
    }

    fun deleteOrder(order: Order) = viewModelScope.launch {
        repository.deleteOrder(order)
    }

    fun selectOrder(orderId: Int?) {
        _selectedOrderId.value = orderId
    }

    // DETALLES DE ORDEN
    fun addOrderDetail(orderId: Int, productId: Int, quantity: Int) = viewModelScope.launch {
        repository.addOrderDetail(OrderDetail(orderId = orderId, productId = productId, quantity = quantity))
    }

    fun updateOrderDetail(detail: OrderDetail) = viewModelScope.launch {
        repository.updateOrderDetail(detail)
    }

    fun deleteOrderDetail(detail: OrderDetail) = viewModelScope.launch {
        repository.deleteOrderDetail(detail)
    }

    // CARGA DE DATOS INICIALES
    fun loadInitialData(
        categories: List<Category>,
        products: List<Product>,
        customers: List<Customer>
    ) = viewModelScope.launch {
        // Limpiar categorías existentes si es necesario
        repository.deleteAllCategories()

        // Insertar datos iniciales
        categories.forEach { repository.addCategory(it) }
        products.forEach { repository.addProduct(it) }
        customers.forEach { repository.addCustomer(it) }
    }
}
