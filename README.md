# 🛒 Bodega Delivery - Aplicación Android

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09.00-blue.svg)](https://developer.android.com/jetpack/compose)
[![Room Database](https://img.shields.io/badge/Room-2.6.1-orange.svg)](https://developer.android.com/training/data-storage/room)

Aplicación Android moderna para la gestión de una **bodega con servicio de delivery**. Desarrollada con **Jetpack Compose**, **Room Database** y arquitectura **MVVM**, implementa un sistema completo de gestión de inventario, clientes y pedidos con carga automática de datos y consultas relacionales avanzadas.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Tecnologías](#-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Base de Datos](#-base-de-datos)
- [Instalación](#-instalación)

---

## ✨ Características

### Funcionalidades Implementadas

#### 🔄 Operaciones CRUD Completas
- **Categorías**: Crear, Leer, Actualizar y Eliminar categorías de productos
- **Productos**: Gestión completa de inventario con precios y categorización
- **Clientes**: Registro y administración de clientes para delivery
- **Pedidos**: Sistema de órdenes con múltiples productos
- **Detalles de Pedido**: Gestión de cantidades y productos por orden

#### 📊 Consultas Relacionales
- **Consulta 1-n (Uno a Muchos)**: Filtrado de productos por categoría
- **Consulta n-m (Muchos a Muchos)**: Relación Order ↔ Product a través de OrderDetail
- **Actualización Reactiva**: Todas las consultas usan Flow para actualizaciones en tiempo real

#### 📥 Carga de Datos Inicial
- **Datos Precargados**: 24 productos de bodega peruana, 6 categorías, 5 clientes
- **Carga Automática**: Se ejecuta solo en el primer inicio de la aplicación
- **Datos Realistas**: Productos típicos con precios en Soles (S/)

#### 🎨 Interfaz de Usuario Minimalista
- **4 Pantallas Principales**: Productos, Categorías, Clientes y Pedidos
- **Navegación Simple**: Bottom Navigation Bar con Material Design 3
- **Diálogos Modales**: Para operaciones CRUD sin pantallas adicionales
- **UI Declarativa**: 100% Jetpack Compose sin XML

---

## 🏗️ Arquitectura

### Patrón MVVM + Repository

```
┌──────────────────────────────────────┐
│         UI Layer (Compose)           │
│  MainScreen, ProductsScreen, etc.    │
└──────────────────────────────────────┘
                 ↓ StateFlow
┌──────────────────────────────────────┐
│       ViewModel (TiendaViewModel)    │
│  - Estados reactivos (StateFlow)     │
│  - Lógica de negocio                 │
└──────────────────────────────────────┘
                 ↓ Flow
┌──────────────────────────────────────┐
│    Repository (TiendaRepository)     │
│  - Abstracción de datos              │
│  - Consultas 1-n y n-m               │
└──────────────────────────────────────┘
                 ↓ Flow
┌──────────────────────────────────────┐
│      Data Layer (DAOs + Room)        │
│  - 5 DAOs (CRUD operations)          │
│  - 5 Entities                        │
│  - SQLite Database                   │
└──────────────────────────────────────┘
```

### Principios Aplicados

- ✅ **Separation of Concerns**: Cada capa tiene responsabilidades específicas
- ✅ **Single Source of Truth**: Room Database como única fuente de verdad
- ✅ **Repository Pattern**: Abstracción del acceso a datos
- ✅ **Reactive Programming**: Flow/StateFlow para actualizaciones automáticas
- ✅ **Dependency Injection Manual**: Inicialización clara y simple

---

## 🛠️ Tecnologías

### Core Technologies

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| **Kotlin** | 2.0.21 | Lenguaje principal (100% Kotlin) |
| **Android Gradle Plugin** | 8.9.1 | Sistema de construcción |
| **Jetpack Compose** | 2024.09.00 | UI moderna y declarativa |
| **Kotlin Coroutines** | Latest | Programación asíncrona |

### Jetpack Libraries

| Librería | Versión | Descripción |
|----------|---------|-------------|
| **Room Database** | 2.6.1 | Persistencia local con SQLite |
| **Room KTX** | 2.6.1 | Extensiones Kotlin para Room |
| **Lifecycle Runtime** | 2.9.4 | Gestión del ciclo de vida |
| **Activity Compose** | 1.11.0 | Integración Activity-Compose |
| **Core KTX** | 1.17.0 | Extensiones Kotlin Android |
| **Material3** | Latest | Componentes Material Design 3 |

### Características Técnicas

- 🔧 **KAPT**: Procesamiento de anotaciones para Room
- 🔄 **Flow & StateFlow**: Programación reactiva
- 🎨 **Material Design 3**: Última versión de Material Design
- 📱 **API 24+**: Compatible con Android 7.0 Nougat en adelante

---

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/tienda/
│
├── MainActivity.kt                     # Activity principal
│
├── data/                              # Capa de datos
│   ├── DataLoader.kt                  # Carga de datos iniciales
│   │
│   ├── dao/                           # Data Access Objects
│   │   ├── CategoryDao.kt            # CRUD de categorías
│   │   ├── CustomerDao.kt            # CRUD de clientes
│   │   ├── OrderDao.kt               # CRUD de órdenes
│   │   ├── OrderDetailDao.kt         # CRUD de detalles
│   │   └── ProductDao.kt             # CRUD de productos
│   │
│   ├── db/                            # Base de datos
│   │   ├── AppDatabase.kt            # Configuración Room
│   │   └── Converters.kt             # Conversores Date↔Long
│   │
│   ├── entities/                      # Entidades Room
│   │   ├── Category.kt               # Categoría (6 precargadas)
│   │   ├── Customer.kt               # Cliente (5 precargados)
│   │   ├── Order.kt                  # Orden de pedido
│   │   ├── OrderDetail.kt            # Detalle (n-m)
│   │   └── Product.kt                # Producto (24 precargados)
│   │
│   └── repository/
│       └── TiendaRepository.kt       # Repositorio CRUD + consultas
│
├── viewmodel/
│   └── TiendaViewModel.kt            # ViewModel con StateFlows
│
└── ui/
    ├── screens/
    │   └── MainScreen.kt             # 4 pantallas (800+ líneas)
    │       ├── ProductsScreen        # Con filtro 1-n
    │       ├── CategoriesScreen      # CRUD simple
    │       ├── CustomersScreen       # CRUD simple
    │       └── OrdersScreen          # Con detalles n-m
    │
    └── theme/
        ├── Color.kt                  # Paleta de colores
        ├── Theme.kt                  # Configuración tema
        └── Type.kt                   # Tipografía
```

### Archivos Clave

| Archivo | Líneas | Función |
|---------|--------|---------|
| `MainScreen.kt` | ~800 | 4 pantallas completas con CRUD |
| `TiendaViewModel.kt` | ~140 | Lógica de negocio y estados |
| `TiendaRepository.kt` | ~60 | CRUD + consultas relacionales |
| `DataLoader.kt` | ~130 | Datos iniciales de bodega |
| `MainActivity.kt` | ~80 | Inicialización y configuración |

---

## 🗄️ Base de Datos

### Esquema Relacional Completo

```
┌──────────────┐         ┌───────────────┐         ┌──────────────┐
│   Customer   │         │    Orders     │         │ OrderDetail  │
├──────────────┤         ├───────────────┤         ├──────────────┤
│ customerId PK│◄───┐    │ orderId PK    │◄───┐    │ orderId PK,FK│
│ firstName    │    └────│ customerId FK │    └────│ productId PK │
│ lastName     │         │ orderDate     │         │ quantity     │
│ email        │         └───────────────┘         └──────────────┘
└──────────────┘                                          │ FK
                                                          ↓
┌──────────────┐         ┌───────────────┐         ┌──────────────┐
│   Category   │         │    Product    │◄────────┘
├──────────────┤         ├───────────────┤
│ categoryId PK│◄───┐    │ productId PK  │
│ name         │    └────│ productName   │
└──────────────┘         │ price         │
                         │ categoryId FK │
                         └───────────────┘
```

### Entidades y Relaciones

#### 1️⃣ **Category** (Categoría)
```kotlin
@Entity(tableName = "Category")
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
    val name: String
)
```
**Relación**: 1 Category → N Products

#### 2️⃣ **Product** (Producto)
```kotlin
@Entity(tableName = "Product")
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    val productName: String,
    val price: Double,
    val categoryId: Int  // FK → Category
)
```
**Relación**: N Products → 1 Category
**Consulta 1-n**: `getProductsByCategory(categoryId)`

#### 3️⃣ **Customer** (Cliente)
```kotlin
@Entity(tableName = "Customer")
data class Customer(
    @PrimaryKey(autoGenerate = true) val customerId: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String
)
```
**Relación**: 1 Customer → N Orders

#### 4️⃣ **Order** (Orden)
```kotlin
@Entity(tableName = "Orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val customerId: Int,  // FK → Customer
    val orderDate: Date
)
```
**Relación**: N Orders → 1 Customer
**Relación**: 1 Order → N OrderDetails

#### 5️⃣ **OrderDetail** (Detalle - Tabla Intermedia)
```kotlin
@Entity(
    tableName = "OrderDetail",
    primaryKeys = ["orderId", "productId"]  // Clave compuesta
)
data class OrderDetail(
    val orderId: Int,    // FK → Order
    val productId: Int,  // FK → Product
    val quantity: Int
)
```
**Relación n-m**: Order ↔ Product
**Consulta n-m**: `getOrderDetails(orderId)`

### Datos Precargados (Primera Ejecución)

#### Categorías (6)
1. Bebidas
2. Abarrotes
3. Lácteos
4. Snacks
5. Limpieza
6. Panadería

#### Productos (24 - Ejemplos)
- **Bebidas**: Coca Cola 1.5L (S/4.50), Inca Kola 2L (S/5.00), Cerveza Pilsen (S/3.50)
- **Abarrotes**: Arroz Costeño 1kg (S/4.20), Azúcar 1kg (S/3.80), Aceite Primor 1L (S/8.50)
- **Lácteos**: Leche Gloria 1L (S/4.50), Yogurt (S/6.00), Queso Fresco (S/12.00)
- **Snacks**: Papas Lays (S/5.50), Doritos (S/5.00), Galletas Oreo (S/8.90)
- **Limpieza**: Detergente Ariel (S/12.50), Lejía Clorox (S/4.50)
- **Panadería**: Pan Francés (S/0.30), Pan Integral (S/0.50), Pan Bimbo (S/6.00)

#### Clientes (5)
- Juan Pérez (juan.perez@email.com)
- María García (maria.garcia@email.com)
- Carlos López (carlos.lopez@email.com)
- Ana Martínez (ana.martinez@email.com)
- Luis Rodríguez (luis.rodriguez@email.com)

---

## 📥 Instalación

### Prerrequisitos

✅ **Android Studio** Hedgehog (2023.1.1) o superior  
✅ **JDK** 11 o superior  
✅ **Android SDK** API 24+ (Android 7.0 Nougat)  
✅ **Gradle** 8.9+ (incluido en wrapper)  

### Pasos de Instalación

#### 1. Clonar el Repositorio
```bash
git clone https://github.com/TAYTOS/Tienda.git
cd Tienda
```

#### 2. Abrir en Android Studio
```
File → Open → Seleccionar carpeta "Tienda"
```

#### 3. Sincronizar Gradle
```
Esperar sincronización automática
O: Build → Rebuild Project
```

#### 4. Ejecutar la Aplicación
```
Run → Run 'app'
O: Shift + F10 (Windows/Linux)
O: Control + R (Mac)
```

### Primera Ejecución

Al ejecutar por primera vez:

1. ✅ La app crea automáticamente la base de datos `tienda_database`
2. ✅ Carga 24 productos, 6 categorías y 5 clientes
3. ✅ Marca `is_first_run = false` en SharedPreferences
4. ✅ En siguientes ejecuciones, los datos persisten (no se recargan)

### Reiniciar Datos

Para volver a cargar los datos iniciales:

**Opción 1**: Desinstalar y reinstalar la app  
**Opción 2**: Borrar datos desde Ajustes → Apps → Tienda → Borrar datos  

---

## 🚀 Uso de la Aplicación

### Pantalla de Productos

- **Ver todos**: Lista completa de productos
- **Filtrar por categoría**: Toca un chip (Consulta 1-n)
- **Agregar producto**: Botón flotante (+)
- **Eliminar**: Ícono de papelera en cada producto

### Pantalla de Categorías

- **Ver categorías**: Lista de todas las categorías
- **Agregar**: Botón flotante (+)
- **Eliminar**: Ícono de papelera

### Pantalla de Clientes

- **Ver clientes**: Lista con nombre, apellido y email
- **Agregar cliente**: Botón flotante (+)
- **Eliminar**: Ícono de papelera

### Pantalla de Pedidos (Delivery)

- **Ver órdenes**: Lista de todos los pedidos
- **Expandir orden**: Click para ver productos (Consulta n-m)
- **Crear pedido**: Botón flotante (+) → seleccionar cliente
- **Agregar productos**: Dentro de orden expandida
- **Eliminar**: Orden completa o productos individuales

---

## 🎯 Consultas Implementadas

### Consulta 1-n: Productos por Categoría

**Ubicación**: Pantalla de Productos  
**Implementación**: Chips de filtro interactivos  

```kotlin
// ViewModel
val filteredProducts = _selectedCategoryId.flatMapLatest { categoryId ->
    if (categoryId != null) {
        repository.getProductsByCategory(categoryId)  // Consulta 1-n
    } else {
        repository.getProducts()
    }
}
```

**Ejemplo**:
```
Categoría: Bebidas
  ├── Coca Cola 1.5L - S/4.50
  ├── Inca Kola 2L - S/5.00
  └── Agua San Luis - S/1.50
```

### Consulta n-m: Detalles de Orden

**Ubicación**: Pantalla de Pedidos (expandible)  
**Implementación**: Vista de detalles al hacer click  

```kotlin
// ViewModel
val orderDetails = _selectedOrderId.flatMapLatest { orderId ->
    if (orderId != null) {
        repository.getOrderDetails(orderId)  // Consulta n-m
    } else {
        flowOf(emptyList())
    }
}
```

**Ejemplo**:
```
Orden #1 (Juan Pérez - 18/11/2025)
  Detalles:
  ├── Coca Cola 1.5L × 2
  ├── Arroz Costeño 1kg × 1
  └── Leche Gloria 1L × 3
```

---

## 📥 Carga de Datos Inicial

### Implementación con DataLoader

La aplicación utiliza la clase `DataLoader.kt` para cargar datos iniciales automáticamente en el primer inicio.

#### Archivos JSON Incluidos

El proyecto incluye **3 archivos JSON** en la carpeta `app/src/main/assets/`:

- 📄 **categories.json** - 6 categorías de productos
- 📄 **products.json** - 24 productos de bodega peruana
- 📄 **customers.json** - 5 clientes de ejemplo

#### ¿Cómo Funciona?

```kotlin
// 1. DataLoader intenta leer archivos JSON desde assets/
fun loadProducts(): List<Product> {
    return try {
        val json = context.assets.open("products.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(json)
        // Parsea el JSON...
    } catch (e: Exception) {
        // 2. Si no encuentra archivos, usa datos por defecto
        getDefaultProducts()
    }
}
```

**Nota**: Como los archivos JSON están incluidos, la app los cargará automáticamente. Los datos por defecto solo se usan como respaldo.

#### Secuencia de Funcionamiento

```
┌─────────────────────────────────────────────────┐
│ 1. Usuario inicia la app por primera vez       │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 2. MainActivity verifica SharedPreferences      │
│    ¿is_first_run == true?                       │
└─────────────────────────────────────────────────┘
                    ↓ SÍ
┌─────────────────────────────────────────────────┐
│ 3. DataLoader intenta cargar desde assets:     │
│    - categories.json                            │
│    - products.json                              │
│    - customers.json                             │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 4. Si no encuentra archivos JSON:              │
│    Usa datos por defecto (getDefaultProducts)  │
│    - 6 Categorías                               │
│    - 24 Productos de bodega peruana             │
│    - 5 Clientes                                 │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 5. ViewModel.loadInitialData() inserta en BD   │
│    (categorías → productos → clientes)          │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 6. Marca is_first_run = false                  │
│    (no se volverán a cargar en próximos usos)   │
└─────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────┐
│ 7. Datos persisten en Room Database            │
│    (SQLite local)                               │
└─────────────────────────────────────────────────┘
```

#### Ejemplo Real de Datos Cargados

**Categoría "Bebidas" (ID = 1)**:
```kotlin
Category(categoryId = 1, name = "Bebidas")
  ↓ 1-n (Una categoría tiene muchos productos)
├── Product(productId = 1, productName = "Coca Cola 1.5L", price = 4.50, categoryId = 1)
├── Product(productId = 2, productName = "Inca Kola 2L", price = 5.00, categoryId = 1)
├── Product(productId = 3, productName = "Agua San Luis 625ml", price = 1.50, categoryId = 1)
└── Product(productId = 4, productName = "Cerveza Pilsen 330ml", price = 3.50, categoryId = 1)
```

#### Código en MainActivity

```kotlin
private fun loadInitialData(viewModel: TiendaViewModel) {
    lifecycleScope.launch {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("is_first_run", true)
        
        if (isFirstRun) {  // Solo se ejecuta la primera vez
            val dataLoader = DataLoader(applicationContext)
            
            viewModel.loadInitialData(
                categories = dataLoader.loadCategories(),  // 6 categorías
                products = dataLoader.loadProducts(),      // 24 productos
                customers = dataLoader.loadCustomers()     // 5 clientes
            )
            
            prefs.edit { putBoolean("is_first_run", false) }
        }
    }
}
```

---

## 🔄 Operaciones CRUD

### Implementación Completa

Todas las entidades tienen operaciones **CRUD** (Create, Read, Update, Delete) implementadas en 3 capas.

#### Arquitectura de CRUD

```
UI (Composable)
    ↓ llama
ViewModel (Lógica)
    ↓ llama
Repository (Abstracción)
    ↓ llama
DAO (Room Database)
    ↓ ejecuta
SQLite (Persistencia)
```

---

### 📖 SELECT (Read) - Consultas

#### SELECT Simple - Todos los Productos

**Secuencia**:
```
1. UI → viewModel.products.collectAsState()
2. ViewModel → repository.getProducts()
3. Repository → productDao.getAll()
4. DAO → SELECT * FROM Product
5. Room → Flow<List<Product>>
6. UI ← Se actualiza automáticamente
```

**Código**:
```kotlin
// DAO
@Query("SELECT * FROM Product")
fun getAll(): Flow<List<Product>>

// Repository
fun getProducts(): Flow<List<Product>> = productDao.getAll()

// ViewModel
val products = repository.getProducts().stateIn(
    viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
)

// UI
val products by viewModel.products.collectAsState()
LazyColumn {
    items(products) { product ->
        ProductItem(product)
    }
}
```

---

#### SELECT 1-n - Productos por Categoría

**Relación**: Una Categoría tiene Muchos Productos

**Secuencia de Funcionamiento**:
```
┌──────────────────────────────────────────┐
│ 1. Usuario toca chip "Bebidas"          │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 2. UI → viewModel.selectCategory(1)     │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 3. ViewModel actualiza:                 │
│    _selectedCategoryId.value = 1        │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 4. flatMapLatest se activa              │
│    (detecta cambio en selectedCategory)  │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 5. Repository ejecuta:                  │
│    getProductsByCategory(categoryId = 1) │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 6. DAO ejecuta query SQL:               │
│    SELECT * FROM Product                │
│    WHERE categoryId = 1                  │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 7. Room retorna Flow<List<Product>>     │
│    Solo productos de "Bebidas"           │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 8. filteredProducts emite nueva lista   │
└──────────────────────────────────────────┘
            ↓
┌──────────────────────────────────────────┐
│ 9. UI se recompone automáticamente      │
│    Muestra solo: Coca Cola, Inca Kola,  │
│    Agua, Cerveza                         │
└──────────────────────────────────────────┘
```

**Código Completo**:
```kotlin
// DAO - Consulta SQL con filtro
@Query("SELECT * FROM Product WHERE categoryId = :id")
fun getByCategory(id: Int): Flow<List<Product>>

// Repository
fun getProductsByCategory(categoryId: Int): Flow<List<Product>> = 
    productDao.getByCategory(categoryId)

// ViewModel - Reactivo con flatMapLatest
private val _selectedCategoryId = MutableStateFlow<Int?>(null)

val filteredProducts = _selectedCategoryId.flatMapLatest { categoryId ->
    if (categoryId != null) {
        repository.getProductsByCategory(categoryId)  // Consulta 1-n
    } else {
        repository.getProducts()  // Todos
    }
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

fun selectCategory(categoryId: Int?) {
    _selectedCategoryId.value = categoryId
}

// UI - Filtros interactivos
LazyRow {
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
```

**Ejemplo Visual**:
```
[Todos] [Bebidas] [Abarrotes] [Lácteos] [Snacks]
   ↑        ↑ (seleccionado)
   
Resultados mostrados (solo categoryId = 1):
┌────────────────────────────────────┐
│ Coca Cola 1.5L        S/ 4.50  🗑️ │
│ Bebidas                            │
├────────────────────────────────────┤
│ Inca Kola 2L          S/ 5.00  🗑️ │
│ Bebidas                            │
├────────────────────────────────────┤
│ Agua San Luis 625ml   S/ 1.50  🗑️ │
│ Bebidas                            │
└────────────────────────────────────┘
```

---

#### SELECT n-m - Detalles de Orden

**Relación**: Una Orden tiene Muchos Productos (a través de OrderDetail)  
**Tabla Intermedia**: OrderDetail con clave compuesta (orderId, productId)

**Secuencia de Funcionamiento**:
```
┌───────────────────────────────────────────┐
│ 1. Usuario hace click en "Orden #1"      │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 2. UI → viewModel.selectOrder(1)         │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 3. ViewModel actualiza:                  │
│    _selectedOrderId.value = 1            │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 4. flatMapLatest detecta cambio          │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 5. Repository ejecuta:                   │
│    getOrderDetails(orderId = 1)          │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 6. DAO ejecuta query SQL:                │
│    SELECT * FROM OrderDetail             │
│    WHERE orderId = 1                      │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 7. Room retorna Flow<List<OrderDetail>>  │
│    [                                      │
│      {orderId:1, productId:1, quantity:2},│
│      {orderId:1, productId:5, quantity:1},│
│      {orderId:1, productId:9, quantity:3} │
│    ]                                      │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 8. UI busca cada Product por productId   │
│    y muestra nombre + cantidad            │
└───────────────────────────────────────────┘
            ↓
┌───────────────────────────────────────────┐
│ 9. Vista expandida muestra:              │
│    - Coca Cola × 2                        │
│    - Arroz Costeño × 1                    │
│    - Leche Gloria × 3                     │
└───────────────────────────────────────────┘
```

**Código Completo**:
```kotlin
// Entidad OrderDetail (Tabla Intermedia)
@Entity(
    tableName = "OrderDetail",
    primaryKeys = ["orderId", "productId"]  // Clave compuesta
)
data class OrderDetail(
    val orderId: Int,      // FK → Order
    val productId: Int,    // FK → Product
    val quantity: Int
)

// DAO
@Query("SELECT * FROM OrderDetail WHERE orderId = :orderId")
fun getDetails(orderId: Int): Flow<List<OrderDetail>>

// Repository
fun getOrderDetails(orderId: Int): Flow<List<OrderDetail>> =
    orderDetailDao.getDetails(orderId)

// ViewModel - Reactivo
private val _selectedOrderId = MutableStateFlow<Int?>(null)

val orderDetails = _selectedOrderId.flatMapLatest { orderId ->
    if (orderId != null) {
        repository.getOrderDetails(orderId)  // Consulta n-m
    } else {
        flowOf(emptyList())
    }
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

fun selectOrder(orderId: Int?) {
    _selectedOrderId.value = orderId
}

// UI - Vista expandible
OrderItem(
    order = order,
    isSelected = selectedOrderId == order.orderId,
    onClick = { 
        viewModel.selectOrder(
            if (selectedOrderId == order.orderId) null else order.orderId
        )
    }
)

// Mostrar detalles si está seleccionado
if (selectedOrderId == order.orderId) {
    val orderDetails by viewModel.orderDetails.collectAsState()
    
    orderDetails.forEach { detail ->
        val product = products.find { it.productId == detail.productId }
        Row {
            Text("${product?.productName}")
            Text("Cantidad: ${detail.quantity}")
            IconButton(onClick = { viewModel.deleteOrderDetail(detail) }) {
                Icon(Icons.Default.Delete, "Eliminar")
            }
        }
    }
}
```

**Ejemplo Visual**:
```
┌─────────────────────────────────────────┐
│ Orden #1                            🗑️  │ ← Click aquí
│ Cliente: Juan Pérez                     │
│ 18/11/2025 14:30                        │
│                                         │
│ Detalles del pedido:                    │ ← Se expande
│ ├─ Coca Cola 1.5L    Cant: 2      🗑️   │
│ ├─ Arroz Costeño 1kg Cant: 1      🗑️   │
│ └─ Leche Gloria 1L   Cant: 3      🗑️   │
│ [+ Agregar producto]                    │
└─────────────────────────────────────────┘
```

---

### ✏️ CREATE (Crear)

**Ejemplo: Crear un Producto**

**Secuencia**:
```
1. Usuario toca FAB (+) en ProductsScreen
2. Se abre diálogo modal
3. Usuario llena: nombre, precio, categoría
4. Usuario toca "Agregar"
5. UI → viewModel.addProduct(name, price, categoryId)
6. ViewModel → repository.addProduct(Product(...))
7. Repository → productDao.insert(product)
8. DAO → INSERT INTO Product VALUES (...)
9. Room actualiza Flow automáticamente
10. UI se recompone mostrando el nuevo producto
```

**Código**:
```kotlin
// ViewModel
fun addProduct(name: String, price: Double, categoryId: Int) = viewModelScope.launch {
    repository.addProduct(Product(productName = name, price = price, categoryId = categoryId))
}

// UI - Diálogo
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
```

---

### 🔄 UPDATE (Actualizar)

**Ejemplo: Actualizar Precio de un Producto**

**Secuencia**:
```
1. Usuario selecciona producto
2. Modifica el precio
3. UI → viewModel.updateProduct(product.copy(price = newPrice))
4. ViewModel → repository.updateProduct(product)
5. Repository → productDao.update(product)
6. DAO → UPDATE Product SET price = ... WHERE productId = ...
7. Room actualiza Flow
8. UI muestra el nuevo precio
```

**Código**:
```kotlin
// DAO
@Update
suspend fun update(product: Product)

// ViewModel
fun updateProduct(product: Product) = viewModelScope.launch {
    repository.updateProduct(product)
}
```

---

### ❌ DELETE (Eliminar)

**Ejemplo: Eliminar un Producto**

**Secuencia**:
```
1. Usuario toca ícono 🗑️ en ProductItem
2. UI → viewModel.deleteProduct(product)
3. ViewModel → repository.deleteProduct(product)
4. Repository → productDao.delete(product)
5. DAO → DELETE FROM Product WHERE productId = ...
6. Room actualiza Flow
7. UI se recompone sin el producto eliminado
```

**Código**:
```kotlin
// DAO
@Delete
suspend fun delete(product: Product)

// ViewModel
fun deleteProduct(product: Product) = viewModelScope.launch {
    repository.deleteProduct(product)
}

// UI
IconButton(onClick = { viewModel.deleteProduct(product) }) {
    Icon(Icons.Default.Delete, "Eliminar")
}
```
---

## 👥 Autores

- **Betanzos Rosas Taylor Anthony**
- **Ccahuana Larota Joshep Antony**
- **Condorios Yllapuma Jorge Enrique**
- **Umasi Coaguila Geraldine Marjorie**
- **Valdivia Luna Carlo Joaquin**

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

---

**⭐ ¡Si este proyecto te fue útil, considera darle una estrella en GitHub!**

