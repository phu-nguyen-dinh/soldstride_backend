# SoleStride Backend

A robust Spring Boot backend for the SoleStride shoe e-commerce application.

## Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.2.2
- **Security**: Spring Security with JWT Authentication
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Documentation**: Comprehensive API documentation included

## Features

- ✅ JWT-based authentication with Bearer tokens
- ✅ Role-based access control (CUSTOMER, ADMIN)
- ✅ RESTful API design
- ✅ Product catalog with filtering
- ✅ Order management system
- ✅ Admin dashboard with analytics
- ✅ Inventory management
- ✅ Global exception handling
- ✅ CORS configuration for frontend integration
- ✅ Database seeding with sample data

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- Docker and Docker Compose (for MySQL)

## Quick Start

### 1. Start MySQL Database

```bash
docker-compose up -d
```

This will start MySQL on port 3306 with the following credentials:
- Database: `solestride_db`
- User: `solestride_user`
- Password: `solestride_pass123`

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Default Users

The application seeds two default users:

### Admin User
- Email: `admin@solestride.com`
- Password: `admin123`
- Role: ADMIN

### Customer User
- Email: `customer@solestride.com`
- Password: `customer123`
- Role: CUSTOMER

## API Endpoints

### Authentication

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "uuid",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "CUSTOMER"
  }
}
```

### Products (Public)

#### Get All Products
```http
GET /api/products
GET /api/products?brand=Nike
GET /api/products?category=Running
GET /api/products?minPrice=50&maxPrice=150
GET /api/products?brand=Nike&category=Running&minPrice=100&maxPrice=200
```

#### Get Product by ID
```http
GET /api/products/{id}
```

### Orders (Authenticated)

#### Get User Orders
```http
GET /api/orders
Authorization: Bearer {token}
```

#### Get Order by ID
```http
GET /api/orders/{id}
Authorization: Bearer {token}
```

#### Create Order
```http
POST /api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "items": [
    {
      "productId": 1,
      "name": "Air Max 270",
      "price": 150.00,
      "quantity": 1,
      "size": 9.0,
      "color": "Black",
      "imageUrl": "https://..."
    }
  ],
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001",
    "country": "USA"
  }
}
```

### Admin Endpoints (Requires ADMIN Role)

#### Get Dashboard Stats
```http
GET /api/admin/stats
Authorization: Bearer {admin-token}
```

#### Get Inventory
```http
GET /api/admin/inventory
Authorization: Bearer {admin-token}
```

#### Create Product
```http
POST /api/admin/products
Authorization: Bearer {admin-token}
Content-Type: application/json

{
  "name": "New Shoe",
  "brand": "Nike",
  "price": 150.00,
  "description": "Description here",
  "imageUrl": "https://...",
  "category": "Running",
  "featured": true,
  "variants": [
    {
      "size": 9.0,
      "color": "Black",
      "stock": 20
    }
  ]
}
```

#### Update Product
```http
PUT /api/admin/products/{id}
Authorization: Bearer {admin-token}
Content-Type: application/json

{
  "name": "Updated Shoe",
  "brand": "Nike",
  "price": 160.00,
  ...
}
```

#### Delete Product
```http
DELETE /api/admin/products/{id}
Authorization: Bearer {admin-token}
```

#### Get All Users
```http
GET /api/admin/users
Authorization: Bearer {admin-token}
```

#### Delete User
```http
DELETE /api/admin/users/{uuid}
Authorization: Bearer {admin-token}
```

#### Update Order Status
```http
PATCH /api/admin/orders/{id}/status
Authorization: Bearer {admin-token}
Content-Type: application/json

{
  "status": "PROCESSING"
}
```

Available order statuses: `PENDING`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED`

## Project Structure

```
solestride-backend/
├── src/
│   └── main/
│       ├── java/com/solestride/
│       │   ├── config/           # Configuration classes
│       │   │   ├── CorsConfig.java
│       │   │   └── SecurityConfig.java
│       │   ├── controller/       # REST Controllers
│       │   │   ├── AdminController.java
│       │   │   ├── AuthController.java
│       │   │   ├── OrderController.java
│       │   │   └── ProductController.java
│       │   ├── dto/             # Data Transfer Objects
│       │   │   ├── AdminDto.java
│       │   │   ├── AuthDto.java
│       │   │   ├── OrderDto.java
│       │   │   └── ProductDto.java
│       │   ├── entity/          # JPA Entities
│       │   │   ├── Address.java
│       │   │   ├── Order.java
│       │   │   ├── OrderItem.java
│       │   │   ├── Product.java
│       │   │   ├── ProductVariant.java
│       │   │   └── User.java
│       │   ├── enums/           # Enumerations
│       │   │   ├── OrderStatus.java
│       │   │   └── UserRole.java
│       │   ├── exception/       # Exception Handling
│       │   │   ├── BadRequestException.java
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   └── ResourceNotFoundException.java
│       │   ├── repository/      # JPA Repositories
│       │   │   ├── OrderRepository.java
│       │   │   ├── ProductRepository.java
│       │   │   ├── ProductVariantRepository.java
│       │   │   └── UserRepository.java
│       │   ├── security/        # Security Components
│       │   │   ├── CustomUserDetailsService.java
│       │   │   ├── JwtAuthenticationFilter.java
│       │   │   └── JwtUtil.java
│       │   ├── service/         # Business Logic
│       │   │   ├── AdminService.java
│       │   │   ├── AuthService.java
│       │   │   ├── OrderService.java
│       │   │   └── ProductService.java
│       │   └── SoleStrideApplication.java
│       └── resources/
│           ├── application.properties
│           └── data.sql
├── docker-compose.yml
└── pom.xml
```

## Configuration

### Application Properties

Key configurations in `application.properties`:

- **Server Port**: 8080
- **Database URL**: `jdbc:mysql://localhost:3306/solestride_db`
- **JWT Secret**: Configured (change in production)
- **JWT Expiration**: 24 hours (86400000 ms)

### CORS Configuration

The application is configured to accept requests from:
- `http://localhost:3000` (React default)
- `http://localhost:5173` (Vite default)

To add more origins, modify `CorsConfig.java`.

## Database Schema

The application uses JPA with `ddl-auto=update` which automatically creates/updates the schema based on entities:

- **users**: User accounts with authentication
- **products**: Product catalog
- **product_variants**: Size, color, and stock variants
- **orders**: Customer orders
- **order_items**: Items within orders

## Security

- Passwords are encrypted using BCrypt
- JWT tokens are signed with HS256 algorithm
- Stateless authentication (no sessions)
- Role-based authorization using Spring Security
- CSRF protection disabled for REST API

## Error Handling

The application provides consistent error responses:

```json
{
  "status": 404,
  "message": "Product not found with id: 1",
  "timestamp": "2024-02-15T12:00:00"
}
```

Common HTTP status codes:
- **200 OK**: Successful GET/PUT/PATCH
- **201 Created**: Successful POST
- **204 No Content**: Successful DELETE
- **400 Bad Request**: Validation errors
- **401 Unauthorized**: Missing/invalid token
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package
java -jar target/solestride-backend-1.0.0.jar
```

## Docker Deployment

To run the entire stack with Docker:

```bash
# Start MySQL
docker-compose up -d

# Build and run the application
mvn clean package
java -jar target/solestride-backend-1.0.0.jar
```

## Troubleshooting

### Database Connection Issues

1. Ensure MySQL is running:
   ```bash
   docker-compose ps
   ```

2. Check MySQL logs:
   ```bash
   docker-compose logs mysql
   ```

3. Verify database credentials in `application.properties`

### JWT Token Issues

- Tokens expire after 24 hours
- Ensure the `Authorization` header format is: `Bearer {token}`
- Check for token validation errors in application logs

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please open an issue in the GitHub repository.
