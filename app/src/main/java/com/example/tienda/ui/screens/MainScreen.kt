package com.example.tienda.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tienda.data.entities.*
import com.example.tienda.viewmodel.TiendaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: TiendaViewModel) {
    var selectedScreen by remember { mutableStateOf(Screen.Products) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bodega - ${selectedScreen.title}") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                Screen.values().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = selectedScreen == screen,
                        onClick = { selectedScreen = screen }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedScreen) {
                Screen.Products -> ProductsScreen(viewModel)
                Screen.Categories -> CategoriesScreen(viewModel)
                Screen.Customers -> CustomersScreen(viewModel)
                Screen.Orders -> OrdersScreen(viewModel)
            }
        }
    }
}

enum class Screen(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Products("Productos", Icons.Default.ShoppingCart),
    Categories("Categorías", Icons.Default.List),
    Customers("Clientes", Icons.Default.Person),
    Orders("Pedidos", Icons.Default.List)
}

// PRODUCTOS
@Composable
fun ProductsScreen(viewModel: TiendaViewModel) {
    val products by viewModel.filteredProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategoryId.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Filtro por categoría
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { viewModel.selectCategory(null) },
                    label = { Text("Todos") }
                )
            }
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category.categoryId,
                    onClick = { viewModel.selectCategory(category.categoryId) },
                    label = { Text(category.name) }
                )
            }
        }

        Divider()

        // Lista de productos
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    categories = categories,
                    onDelete = { viewModel.deleteProduct(product) }
                )
            }
        }

        // Botón agregar
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, "Agregar Producto")
        }
    }

    if (showDialog) {
        AddProductDialog(
            categories = categories,
            onDismiss = { showDialog = false },
            onConfirm = { name, price, categoryId ->
                viewModel.addProduct(name, price, categoryId)
                showDialog = false
            }
        )
    }
}

@Composable
fun ProductItem(
    product: Product,
    categories: List<Category>,
    onDelete: () -> Unit
) {
    val category = categories.find { it.categoryId == product.categoryId }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.productName, fontWeight = FontWeight.Bold)
                Text("S/ ${String.format("%.2f", product.price)}",
                     style = MaterialTheme.typography.bodyLarge,
                     color = MaterialTheme.colorScheme.primary)
                Text(category?.name ?: "Sin categoría",
                     style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    categories: List<Category>,
    onDismiss: () -> Unit,
    onConfirm: (String, Double, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf(categories.firstOrNull()?.categoryId ?: 1) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Producto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = categories.find { it.categoryId == selectedCategoryId }?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategoryId = category.categoryId
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    price.toDoubleOrNull()?.let { p ->
                        if (name.isNotBlank()) {
                            onConfirm(name, p, selectedCategoryId)
                        }
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// CATEGORÍAS
@Composable
fun CategoriesScreen(viewModel: TiendaViewModel) {
    val categories by viewModel.categories.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    category = category,
                    onDelete = { viewModel.deleteCategory(category) }
                )
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, "Agregar Categoría")
        }
    }

    if (showDialog) {
        AddCategoryDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name ->
                viewModel.addCategory(name)
                showDialog = false
            }
        )
    }
}

@Composable
fun CategoryItem(category: Category, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(category.name, fontWeight = FontWeight.Bold)
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Categoría") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name)
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// CLIENTES
@Composable
fun CustomersScreen(viewModel: TiendaViewModel) {
    val customers by viewModel.customers.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(customers) { customer ->
                CustomerItem(
                    customer = customer,
                    onDelete = { viewModel.deleteCustomer(customer) }
                )
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, "Agregar Cliente")
        }
    }

    if (showDialog) {
        AddCustomerDialog(
            onDismiss = { showDialog = false },
            onConfirm = { firstName, lastName, email ->
                viewModel.addCustomer(firstName, lastName, email)
                showDialog = false
            }
        )
    }
}

@Composable
fun CustomerItem(customer: Customer, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${customer.firstName} ${customer.lastName}", fontWeight = FontWeight.Bold)
                Text(customer.email, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun AddCustomerDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Cliente") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                        onConfirm(firstName, lastName, email)
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// ÓRDENES
@Composable
fun OrdersScreen(viewModel: TiendaViewModel) {
    val orders by viewModel.orders.collectAsState()
    val customers by viewModel.customers.collectAsState()
    val orderDetails by viewModel.orderDetails.collectAsState()
    val products by viewModel.products.collectAsState()
    val selectedOrderId by viewModel.selectedOrderId.collectAsState()

    var showOrderDialog by remember { mutableStateOf(false) }
    var showDetailDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Lista de órdenes
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(orders) { order ->
                val customer = customers.find { it.customerId == order.customerId }
                OrderItem(
                    order = order,
                    customer = customer,
                    isSelected = selectedOrderId == order.orderId,
                    onClick = {
                        viewModel.selectOrder(
                            if (selectedOrderId == order.orderId) null else order.orderId
                        )
                    },
                    onDelete = { viewModel.deleteOrder(order) }
                )

                // Mostrar detalles si está seleccionado (Consulta n-m)
                if (selectedOrderId == order.orderId) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Detalles del pedido:", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            orderDetails.forEach { detail ->
                                val product = products.find { it.productId == detail.productId }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${product?.productName ?: "Producto"}")
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("Cant: ${detail.quantity}")
                                        IconButton(
                                            onClick = { viewModel.deleteOrderDetail(detail) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                "Eliminar",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(onClick = { showDetailDialog = true }) {
                                Icon(Icons.Default.Add, "Agregar producto")
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Agregar producto")
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showOrderDialog = true },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, "Nueva Orden")
        }
    }

    if (showOrderDialog) {
        AddOrderDialog(
            customers = customers,
            onDismiss = { showOrderDialog = false },
            onConfirm = { customerId ->
                viewModel.addOrder(customerId)
                showOrderDialog = false
            }
        )
    }

    if (showDetailDialog && selectedOrderId != null) {
        AddOrderDetailDialog(
            products = products,
            onDismiss = { showDetailDialog = false },
            onConfirm = { productId, quantity ->
                viewModel.addOrderDetail(selectedOrderId!!, productId, quantity)
                showDetailDialog = false
            }
        )
    }
}

@Composable
fun OrderItem(
    order: Order,
    customer: Customer?,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Orden #${order.orderId}", fontWeight = FontWeight.Bold)
                Text("Cliente: ${customer?.firstName} ${customer?.lastName}")
                Text(
                    java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
                        .format(order.orderDate),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderDialog(
    customers: List<Customer>,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedCustomerId by remember { mutableStateOf(customers.firstOrNull()?.customerId ?: 1) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Orden") },
        text = {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = customers.find { it.customerId == selectedCustomerId }?.let {
                        "${it.firstName} ${it.lastName}"
                    } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Cliente") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    customers.forEach { customer ->
                        DropdownMenuItem(
                            text = { Text("${customer.firstName} ${customer.lastName}") },
                            onClick = {
                                selectedCustomerId = customer.customerId
                                expanded = false
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selectedCustomerId) }) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderDetailDialog(
    products: List<Product>,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    var selectedProductId by remember { mutableStateOf(products.firstOrNull()?.productId ?: 1) }
    var quantity by remember { mutableStateOf("1") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Producto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = products.find { it.productId == selectedProductId }?.productName ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Producto") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        products.forEach { product ->
                            DropdownMenuItem(
                                text = { Text("${product.productName} - S/${product.price}") },
                                onClick = {
                                    selectedProductId = product.productId
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    quantity.toIntOrNull()?.let { q ->
                        if (q > 0) {
                            onConfirm(selectedProductId, q)
                        }
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
