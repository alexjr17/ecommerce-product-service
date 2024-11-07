# Product Service

Servicio de gestión de productos implementado con Spring WebFlux y R2DBC, siguiendo una arquitectura hexagonal.

## Tecnologías Utilizadas

- Spring Boot 3.3.5
- Spring WebFlux
- R2DBC con MySQL
- Docker
- Gradle
- JUnit
- Mockito
- webTestClient

## Arquitectura

El proyecto sigue una arquitectura hexagonal (ports and adapters) con tres capas principales:

1. **Domain**: Contiene la lógica de negocio y modelos
2. **Application**: Servicios de aplicación y casos de uso
3. **Infrastructure**: Adaptadores para la persistencia y API REST
```
src/
├── main/
│   ├── java/
│   │   └── com/ecommerce/product_service/
│   │       ├── application/
│   │       ├── domain/
│   │       └── infrastructure/
│   └── resources/
│       ├── application.yml
│       └── schema.sql
```

## Diagrama de Arquitectura

```mermaid
graph TB
    subgraph "Frontend"
        CLI[Cliente Web/Mobile]
    end

    subgraph "API Gateway"
        APIG[API Gateway]
    end

    subgraph "Authentication"
        Auth[Auth Service]
        JWT[JWT Token Handler]
    end

    subgraph "Product Catalog Service"
        PC[Product API]
        PL[Product Logic]
        PR[Product Repository]
        PC --> PL
        PL --> PR
        PR --> PDB[(Product DB)]
    end

    subgraph "Order Management Service"
        OC[Order API]
        OL[Order Logic]
        OR[Order Repository]
        OC --> OL
        OL --> OR
        OR --> ODB[(Order DB)]
    end

    subgraph "Shared Infrastructure"
        Log[Logging Service]
        Mon[Monitoring]
        Cache[Redis Cache]
    end

    CLI --> APIG
    APIG --> Auth
    Auth --> JWT
    APIG --> PC
    APIG --> OC
    
    PC --> Log
    OC --> Log
    PC --> Cache
    OC --> Cache
```
## Requisitos Previos

- Docker
- Docker Compose

## Configuración y Ejecución

### 1. Clonar el Repositorio

```bash
git clone https://github.com/alexjr17/ecommerce-product-service.git
cd product-service
```

### 2. Construcción y Ejecución con Docker

```bash
# Construir y ejecutar los contenedores
docker-compose up --build

# Detener los contenedores
docker-compose down
```

La aplicación estará disponible en:
- API: http://localhost:8080
- API: http://localhost:8081 //puede cambiar el puerto
- Base de datos MySQL: puerto 3306

### 3. Endpoints Disponibles

```
Productus
- GET `/api/products` - Listar todos los productos
- GET `/api/products/{id}` - Obtener un producto por ID
- POST `/api/products` - Crear un nuevo producto
- PUT `/api/products/{id}` - Actualizar un producto
- DELETE `/api/products/{id}` - Eliminar un producto

Ordenes
- GET `/api/orders` - Listar todos los productos
- GET `/api/orders/{id}` - Obtener un producto por ID
- POST `/api/orders` - Crear un nuevo producto
- PUT `/api/orders/{id}` - Actualizar un producto
- DELETE `/api/orders/{id}` - Eliminar un producto
```
### 4. Ejemplo de Petición POST

```bash
curl -X POST http://localhost:8081/api/products \
-H "Content-Type: application/json" \
-d '{
    "name": "Laptop",
    "description": "High performance laptop",
    "price": 999.99,
    "stock": 10
}'
```
# Documentación de la API de ecommerce
Aquí puedes acceder a la documentación completa de los recursos y endpoints de nuestra API de ecommerce:

[Ver documentación en Postman](https://elements.getpostman.com/redirect?entityId=20219157-0ee01da0-aa17-4711-b09c-7d7c3d2bc2bf&entityType=collection)

## Pruebas

Para ejecutar las pruebas:

```bash
# Localmente con Gradle
./gradlew test

# Con Docker
docker-compose run --rm product-service ./gradlew test
```

## Configuración Docker

El proyecto incluye:

1. **Dockerfile**: Construye la imagen de la aplicación
2. **docker-compose.yml**: Orquesta la aplicación y la base de datos
3. **application-docker.yml**: Configuración específica para Docker

### Características del Dockerfile

- Build multi-etapa para optimizar el tamaño de la imagen
- Base en OpenJDK 17
- Configuración de puertos y variables de entorno
- Gradle como herramienta de construcción

### Docker Compose

Servicios configurados:
- MySQL 8.0
- Aplicación Spring Boot
- Volúmenes persistentes para la base de datos
- Healthchecks para garantizar el inicio correcto

## Mantenimiento

Para actualizar la aplicación:

1. Realizar cambios en el código
2. Reconstruir la imagen: `docker-compose up --build`
3. Los datos persistirán en el volumen de MySQL

## Troubleshooting

Si encuentras problemas:

1. Verificar logs: `docker-compose logs`
2. Reiniciar contenedores: `docker-compose restart`
3. Reconstruir desde cero: `docker-compose down -v && docker-compose up --build`

## Tablas Mysql
```
por si falla el docker
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS db_ecommerce
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE db_ecommerce;

-- Creación de la tabla users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar un usuario
INSERT INTO users (username, email, password) VALUES 
('juan123', 'juan@example.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewYpQYxYsC.niBim');

-- Creación de la tabla products
CREATE TABLE IF NOT EXISTS products (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    active BOOLEAN DEFAULT true
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar un producto
INSERT INTO products (name, description, price, stock, active) VALUES
('Laptop HP', 'Laptop HP 15.6" 8GB RAM 256GB SSD', 799.99, 50, true);

-- Creación de la tabla orders
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT UNSIGNED NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    quantity INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar una orden (ejemplo con user_id NULL)
INSERT INTO orders (user_id, product_id, quantity, total, status) VALUES
(NULL, 1, 1, 799.99, 'pending');
```
