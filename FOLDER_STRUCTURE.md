# SoleStride Backend - Folder Structure

This document provides a detailed overview of the project structure and the purpose of each component.

## Root Directory

```
solestride-backend/
├── src/                          # Source code
├── target/                       # Compiled code (generated)
├── .gitignore                   # Git ignore rules
├── API_DOCUMENTATION.md         # Complete API reference
├── docker-compose.yml           # MySQL container configuration
├── pom.xml                      # Maven dependencies and build config
├── QUICKSTART.md               # Quick start guide
├── README.md                    # Main documentation
└── FOLDER_STRUCTURE.md         # This file
```

## Source Directory Structure

```
src/
└── main/
    ├── java/com/solestride/     # Java source code
    └── resources/               # Configuration files
```

## Java Package Structure

### Overview
```
src/main/java/com/solestride/
├── config/                      # Configuration classes
├── controller/                  # REST API endpoints
├── dto/                        # Data Transfer Objects
├── entity/                     # Database entities
├── enums/                      # Enumerations
├── exception/                  # Exception handling
├── repository/                 # Data access layer
├── security/                   # Authentication & authorization
├── service/                    # Business logic
└── SoleStrideApplication.java  # Main application class
```

## Detailed Package Breakdown

### 1. Configuration (`config/`)

Handles application configuration and cross-cutting concerns.

```
config/
├── CorsConfig.java             # CORS (Cross-Origin) configuration
└── SecurityConfig.java         # Spring Security configuration
```

**CorsConfig.java**
- Purpose: Enable frontend to communicate with backend
- Allows: localhost:3000 (React) and localhost:5173 (Vite)
- Configures: HTTP methods, headers, credentials

**SecurityConfig.java**
- Purpose: Define security rules and authentication
- Configures: JWT authentication, endpoint permissions
- Defines: Public vs protected routes, role-based access

### 2. Controllers (`controller/`)

Handle HTTP requests and responses (REST API layer).

```
controller/
├── AdminController.java        # Admin-only endpoints
├── AuthController.java         # Authentication endpoints
├── OrderController.java        # Order management
└── ProductController.java      # Product catalog
```

**AuthController.java**
- `/api/auth/register` - User registration
- `/api/auth/login` - User login
- Returns: JWT tokens

**ProductController.java**
- `/api/products` - Get all products (with filters)
- `/api/products/{id}` - Get single product
- Public access (no auth required)

**OrderController.java**
- `/api/orders` - Get user's orders
- `/api/orders/{id}` - Get specific order
- `/api/orders` - Create new order
- Requires: Authentication

**AdminController.java**
- `/api/admin/stats` - Dashboard statistics
- `/api/admin/inventory` - Inventory management
- `/api/admin/products/*` - Product CRUD
- `/api/admin/users/*` - User management
- `/api/admin/orders/{id}/status` - Update order status
- Requires: ADMIN role

### 3. DTOs (`dto/`)

Data Transfer Objects for API requests/responses.

```
dto/
├── AdminDto.java               # Admin dashboard data structures
├── AuthDto.java                # Authentication request/response
├── OrderDto.java               # Order-related DTOs
└── ProductDto.java             # Product-related DTOs
```

**Why DTOs?**
- Separate API contracts from database entities
- Control what data is exposed to clients
- Enable request validation
- Allow flexible response formatting

**Key DTOs:**
- `AuthDto.RegisterRequest` - User registration data
- `AuthDto.LoginRequest` - Login credentials
- `AuthDto.AuthResponse` - Login response with token
- `ProductDto.ProductRequest` - Create/update product
- `ProductDto.ProductResponse` - Product data for client
- `OrderDto.CreateOrderRequest` - New order data
- `OrderDto.OrderResponse` - Order data with items
- `AdminDto.DashboardStats` - Analytics data

### 4. Entities (`entity/`)

JPA entities representing database tables.

```
entity/
├── Address.java                # Embedded address (in Order)
├── Order.java                  # Customer orders
├── OrderItem.java              # Items in an order
├── Product.java                # Product catalog
├── ProductVariant.java         # Size/color/stock variants
└── User.java                   # User accounts
```

**Relationships:**
- **User** ↔ **Order**: One user can have many orders
- **Product** ↔ **ProductVariant**: One product has many variants
- **Order** ↔ **OrderItem**: One order has many items

**Key Features:**
- Auto-generated IDs (UUID for User, Long for others)
- Timestamps (createdAt, updatedAt)
- Cascade operations (delete product → delete variants)
- Fetch strategies (EAGER vs LAZY loading)

### 5. Enums (`enums/`)

Enumeration types for type-safe constants.

```
enums/
├── OrderStatus.java            # Order lifecycle states
└── UserRole.java              # User permission levels
```

**OrderStatus:**
- `PENDING` - Order placed, awaiting processing
- `PROCESSING` - Order being prepared
- `SHIPPED` - Order dispatched
- `DELIVERED` - Order received by customer
- `CANCELLED` - Order cancelled

**UserRole:**
- `CUSTOMER` - Regular user (default)
- `ADMIN` - Administrator with full access

### 6. Exceptions (`exception/`)

Custom exceptions and global error handling.

```
exception/
├── BadRequestException.java            # 400 errors
├── GlobalExceptionHandler.java         # Centralized error handling
└── ResourceNotFoundException.java      # 404 errors
```

**GlobalExceptionHandler.java**
- Catches all exceptions
- Returns consistent error format
- Maps exceptions to HTTP status codes
- Handles validation errors

**Error Response Format:**
```json
{
  "status": 404,
  "message": "Product not found with id: 1",
  "timestamp": "2024-02-15T12:00:00"
}
```

### 7. Repositories (`repository/`)

Data access layer using Spring Data JPA.

```
repository/
├── OrderRepository.java            # Order database operations
├── ProductRepository.java          # Product database operations
├── ProductVariantRepository.java   # Variant database operations
└── UserRepository.java            # User database operations
```

**Features:**
- Automatic CRUD operations
- Custom query methods
- JPA Specifications for complex filtering
- Aggregation queries for statistics

**Example Methods:**
- `findByEmail(String email)` - Find user by email
- `findByUserIdOrderByCreatedAtDesc()` - Get user's orders
- `calculateTotalRevenue()` - Sum all order totals

### 8. Security (`security/`)

Authentication and authorization components.

```
security/
├── CustomUserDetailsService.java   # Load user for authentication
├── JwtAuthenticationFilter.java    # Intercept requests, validate JWT
└── JwtUtil.java                   # Generate and validate tokens
```

**Authentication Flow:**
1. User logs in → `AuthController`
2. Credentials validated → `CustomUserDetailsService`
3. JWT token generated → `JwtUtil`
4. Token returned to client
5. Client includes token in `Authorization` header
6. `JwtAuthenticationFilter` validates token on each request
7. User info loaded into security context

**JWT Token:**
- Algorithm: HS256 (HMAC with SHA-256)
- Expiration: 24 hours (86400000 ms)
- Contains: username (email)
- Signed with: secret key

### 9. Services (`service/`)

Business logic layer (between controllers and repositories).

```
service/
├── AdminService.java          # Admin operations & analytics
├── AuthService.java           # Authentication logic
├── OrderService.java          # Order processing
└── ProductService.java        # Product management
```

**Why Services?**
- Separate business logic from controllers
- Reusable across multiple controllers
- Transactional operations
- Easier to test

**Key Responsibilities:**
- **AuthService**: Register users, validate login, generate tokens
- **ProductService**: CRUD operations, filtering, variant management
- **OrderService**: Create orders, calculate totals, retrieve user orders
- **AdminService**: Analytics, statistics, inventory tracking

### 10. Main Application Class

```
SoleStrideApplication.java      # Spring Boot entry point
```

**Purpose:**
- Main method to start the application
- `@SpringBootApplication` annotation enables auto-configuration
- Component scanning starts from this package

## Resources Directory

```
src/main/resources/
├── application.properties     # Application configuration
└── data.sql                  # Database seed data
```

**application.properties**
- Server configuration (port, context path)
- Database connection settings
- JPA/Hibernate configuration
- JWT settings (secret, expiration)
- Logging levels

**data.sql**
- Executes on application startup
- Seeds database with sample products
- Creates default admin and customer users
- Adds product variants with stock levels

## Build Configuration

### pom.xml

Maven configuration file defining:

**Dependencies:**
- Spring Boot Starter Web
- Spring Boot Data JPA
- Spring Boot Security
- MySQL Connector
- JWT libraries (jjwt)
- Lombok (reduce boilerplate)
- Spring Boot DevTools (development)

**Build Configuration:**
- Java version: 21
- Spring Boot parent version: 3.2.2
- Maven plugins for building JAR

**Profiles:**
- Default: Development settings
- Can add: Production, Testing profiles

## Docker Configuration

### docker-compose.yml

Defines MySQL container:
- Image: mysql:8.0
- Port: 3306
- Database: solestride_db
- User credentials
- Volume for data persistence
- Health check configuration

## Documentation Files

### README.md
- Project overview
- Setup instructions
- API endpoint summary
- Configuration guide
- Troubleshooting

### API_DOCUMENTATION.md
- Complete API reference
- Request/response examples
- cURL commands
- Error responses
- Testing instructions

### QUICKSTART.md
- 5-minute setup guide
- Basic testing commands
- Common issues and fixes
- Next steps for development

### FOLDER_STRUCTURE.md
- This file
- Detailed package explanations
- Architecture overview
- Component purposes

## Architecture Layers

```
┌─────────────────────────────────────┐
│         Controllers                 │ ← REST API Layer
│  (AuthController, ProductController)│
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│           Services                  │ ← Business Logic
│   (AuthService, ProductService)     │
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│         Repositories                │ ← Data Access
│  (UserRepository, ProductRepository)│
└─────────────────────────────────────┘
                ↓
┌─────────────────────────────────────┐
│       MySQL Database                │ ← Data Storage
└─────────────────────────────────────┘
```

**Cross-Cutting Concerns:**
- Security filters intercept all requests
- Exception handlers catch errors from any layer
- DTOs transform data between layers
- Configuration applied application-wide

## Key Design Patterns

1. **MVC (Model-View-Controller)**
   - Model: Entities
   - View: DTOs (API responses)
   - Controller: Controllers

2. **Repository Pattern**
   - Abstracts data access
   - Spring Data JPA implementation

3. **Service Layer Pattern**
   - Encapsulates business logic
   - Promotes reusability

4. **DTO Pattern**
   - Separates API from domain model
   - Controls data exposure

5. **Dependency Injection**
   - Spring manages component lifecycle
   - Constructor injection with Lombok

## Best Practices Applied

✅ **Separation of Concerns**: Each layer has distinct responsibility
✅ **DRY Principle**: Reusable services and repositories
✅ **Single Responsibility**: Classes have one clear purpose
✅ **RESTful Design**: Standard HTTP methods and status codes
✅ **Security First**: JWT authentication, password encryption
✅ **Error Handling**: Consistent error responses
✅ **Documentation**: Comprehensive comments and docs
✅ **Testing Ready**: Service layer easily testable

## Next Steps for Developers

1. **Start with Controllers**: Understand API endpoints
2. **Review Services**: See business logic implementation
3. **Study Entities**: Learn data model relationships
4. **Explore Security**: Understand JWT flow
5. **Check Repositories**: See custom query examples
6. **Read DTOs**: Understand data transformation

## Extensibility Points

Want to add features? Consider:

- **New Entity**: Add in `entity/`, create repository
- **New Endpoint**: Add controller method, service logic
- **New Filter**: Implement in `ProductService.getAllProducts()`
- **New Role**: Add to `UserRole` enum, update security config
- **New Validation**: Add annotations in DTOs
- **New Exception**: Create in `exception/`, handle globally

This structure provides a solid foundation for scaling the application!
