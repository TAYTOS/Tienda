# 🛒 Tienda - Aplicación Android de Gestión de Tienda

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2024+-green.svg)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.0-blue.svg)](https://developer.android.com/jetpack/compose)
[![Room Database](https://img.shields.io/badge/Room-2.6.1-orange.svg)](https://developer.android.com/training/data-storage/room)

Aplicación Android nativa desarrollada con **Jetpack Compose** y **Room Database** para la gestión integral de una tienda online. Este proyecto implementa un sistema completo de gestión de productos, categorías, clientes y órdenes utilizando las mejores prácticas de desarrollo Android moderno.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Tecnologías](#-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Base de Datos](#-base-de-datos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [API del Repositorio](#-api-del-repositorio)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

---

## ✨ Características

### Funcionalidades Principales

- ✅ **Gestión de Productos**: CRUD completo de productos con categorización
- ✅ **Categorías**: Organización jerárquica de productos
- ✅ **Clientes**: Registro y administración de información de clientes
- ✅ **Órdenes**: Sistema de pedidos con detalles de productos
- ✅ **Persistencia Local**: Base de datos SQLite con Room
- ✅ **Interfaz Moderna**: UI construida con Jetpack Compose
- ✅ **Programación Reactiva**: Flow y Coroutines para operaciones asíncronas
- ✅ **Arquitectura Limpia**: Separación en capas (Data, Domain, Presentation)

---

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura de capas** limpia y mantenible:

```
┌─────────────────────────────────────┐
│         Presentation Layer          │
│    (MainActivity, Composables)      │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│         Repository Layer            │
│      (TiendaRepository)             │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│         Data Layer                  │
│  (DAOs, Entities, Database)         │
└─────────────────────────────────────┘
```

### Patrón de Diseño

- **Repository Pattern**: Para abstraer el acceso a datos
- **MVVM** (preparado para implementación): Separación de lógica de negocio y UI
- **Single Source of Truth**: Room Database como única fuente de verdad

---

## 🛠️ Tecnologías

### Core

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **Kotlin** | 2.0.21 | Lenguaje de programación principal |
| **Android Gradle Plugin** | 8.9.1 | Sistema de construcción |
| **Compose Compiler** | 2.0.21 | Compilador de Jetpack Compose |

### Jetpack Libraries

| Librería | Versión | Descripción |
|----------|---------|-------------|
| **Room** | 2.6.1 | Base de datos SQLite ORM |
| **Compose BOM** | 2024.09.00 | UI toolkit declarativo |
| **Material3** | Latest | Componentes Material Design 3 |
| **Lifecycle Runtime** | 2.9.4 | Gestión del ciclo de vida |
| **Activity Compose** | 1.11.0 | Integración Activity-Compose |
| **Core KTX** | 1.17.0 | Extensiones Kotlin para Android |

---

## 📁 Estructura del Proyecto

```
com.example.tienda/
│
├── data/                           # Capa de datos
│   ├── dao/                       # Data Access Objects
│   │   ├── CategoryDao.kt        # CRUD de categorías
│   │   ├── CustomerDao.kt        # CRUD de clientes
│   │   ├── OrderDao.kt           # CRUD de órdenes
│   │   ├── OrderDetailDao.kt     # CRUD de detalles de orden
│   │   └── ProductDao.kt         # CRUD de productos
│   │
│   ├── db/                        # Configuración de base de datos
│   │   ├── AppDatabase.kt        # Instancia de Room Database
│   │   └── Converters.kt         # Conversores de tipo (Date ↔ Long)
│   │
│   ├── entities/                  # Modelos de datos (Entidades Room)
│   │   ├── Category.kt           # Entidad Categoría
│   │   ├── Customer.kt           # Entidad Cliente
│   │   ├── Order.kt              # Entidad Orden
│   │   ├── OrderDetail.kt        # Entidad Detalle de Orden
│   │   └── Product.kt            # Entidad Producto
│   │
│   └── repository/                # Repositorios
│       └── TiendaRepository.kt   # Repositorio centralizado
│
├── ui/                            # Capa de presentación
│   └── theme/                    # Tema de la aplicación
│       ├── Color.kt              # Paleta de colores
│       ├── Theme.kt              # Configuración del tema
│       └── Type.kt               # Tipografía
│
└── MainActivity.kt                # Actividad principal
```

---

## 🗄️ Base de Datos

### Esquema de Relaciones

```sql
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│  Customer   │         │    Orders    │         │ OrderDetail │
├─────────────┤         ├──────────────┤         ├─────────────┤
│ customerId  │◄────┐   │ orderId      │◄────┐   │ orderId     │
│ firstName   │     └───│ customerId   │     └───│ productId   │
│ lastName    │         │ orderDate    │         │ quantity    │
│ email       │         └──────────────┘         └─────────────┘
└─────────────┘                                         │
                                                        │
┌─────────────┐         ┌──────────────┐               │
│  Category   │         │   Product    │◄──────────────┘
├─────────────┤         ├──────────────┤
│ categoryId  │◄────┐   │ productId    │
│ name        │     └───│ productName  │
└─────────────┘         │ price        │
                        │ categoryId   │
                        └──────────────┘
```
---

## 📥 Instalación

### Prerrequisitos

- **Android Studio** Hedgehog | 2023.1.1 o superior
- **JDK** 11 o superior
- **Android SDK** API 24+ (Android 7.0 Nougat)
- **Gradle** 8.9+ (incluido en el wrapper)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/TAYTOS/Tienda.git
cd Tienda
```

2. **Abrir en Android Studio**
```
File > Open > Seleccionar la carpeta del proyecto
```

3. **Sincronizar dependencias**
```
Esperar a que Gradle sincronice automáticamente
O ejecutar: ./gradlew build
```

4. **Ejecutar la aplicación**
```
Run > Run 'app' o presionar Shift + F10
```
---

## 👥 Autores

- **TAYTOS** - [GitHub](https://github.com/TAYTOS)

---