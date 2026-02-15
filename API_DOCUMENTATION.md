# SoleStride API Documentation

## Base URL
```
http://localhost:8080
```

## Authentication

The API uses JWT (JSON Web Token) for authentication. After logging in or registering, you'll receive a token that must be included in subsequent requests.

### Header Format
```
Authorization: Bearer <your-jwt-token>
```

---

## Endpoints

### 1. Authentication

#### 1.1 Register User
Create a new customer account.

**Endpoint:** `POST /api/auth/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "CUSTOMER"
  }
}
```

**Validation:**
- `name`: Required
- `email`: Required, valid email format
- `password`: Required, minimum 6 characters

---

#### 1.2 Login
Authenticate existing user.

**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "CUSTOMER"
  }
}
```

**Error Response:** `401 Unauthorized`
```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2024-02-15T12:00:00"
}
```

---

### 2. Products (Public Access)

#### 2.1 Get All Products
Retrieve a list of all products with optional filtering.

**Endpoint:** `GET /api/products`

**Query Parameters:**
- `brand` (optional): Filter by brand (e.g., Nike, Adidas)
- `category` (optional): Filter by category (e.g., Running, Casual)
- `minPrice` (optional): Minimum price filter
- `maxPrice` (optional): Maximum price filter

**Examples:**
```
GET /api/products
GET /api/products?brand=Nike
GET /api/products?category=Running
GET /api/products?minPrice=50&maxPrice=150
GET /api/products?brand=Nike&category=Running&minPrice=100&maxPrice=200
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Air Max 270",
    "brand": "Nike",
    "price": 150.00,
    "description": "Nike's first lifestyle Air Max...",
    "imageUrl": "https://images.unsplash.com/...",
    "category": "Running",
    "featured": true,
    "variants": [
      {
        "size": 9.0,
        "color": "Black",
        "stock": 25
      },
      {
        "size": 9.5,
        "color": "Black",
        "stock": 20
      }
    ]
  }
]
```

---

#### 2.2 Get Product by ID
Retrieve a specific product by its ID.

**Endpoint:** `GET /api/products/{id}`

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Air Max 270",
  "brand": "Nike",
  "price": 150.00,
  "description": "Nike's first lifestyle Air Max...",
  "imageUrl": "https://images.unsplash.com/...",
  "category": "Running",
  "featured": true,
  "variants": [
    {
      "size": 9.0,
      "color": "Black",
      "stock": 25
    }
  ]
}
```

**Error Response:** `404 Not Found`
```json
{
  "status": 404,
  "message": "Product not found with id: 999",
  "timestamp": "2024-02-15T12:00:00"
}
```

---

### 3. Orders (Authenticated Users)

#### 3.1 Get User Orders
Retrieve all orders for the authenticated user.

**Endpoint:** `GET /api/orders`

**Headers:**
```
Authorization: Bearer <token>
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "items": [
      {
        "productId": 1,
        "name": "Air Max 270",
        "price": 150.00,
        "quantity": 2,
        "size": 9.0,
        "color": "Black",
        "imageUrl": "https://..."
      }
    ],
    "total": 300.00,
    "status": "DELIVERED",
    "shippingAddress": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zip": "10001",
      "country": "USA"
    },
    "createdAt": "2024-02-10T10:30:00"
  }
]
```

---

#### 3.2 Get Order by ID
Retrieve a specific order by ID.

**Endpoint:** `GET /api/orders/{id}`

**Headers:**
```
Authorization: Bearer <token>
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "items": [...],
  "total": 300.00,
  "status": "DELIVERED",
  "shippingAddress": {...},
  "createdAt": "2024-02-10T10:30:00"
}
```

---

#### 3.3 Create Order
Place a new order.

**Endpoint:** `POST /api/orders`

**Headers:**
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
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

**Response:** `201 Created`
```json
{
  "id": 5,
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "items": [...],
  "total": 150.00,
  "status": "PENDING",
  "shippingAddress": {...},
  "createdAt": "2024-02-15T12:00:00"
}
```

---

### 4. Admin Endpoints (ADMIN Role Required)

#### 4.1 Get Dashboard Statistics
Retrieve comprehensive dashboard statistics.

**Endpoint:** `GET /api/admin/stats`

**Headers:**
```
Authorization: Bearer <admin-token>
```

**Response:** `200 OK`
```json
{
  "revenue": 15420.50,
  "ordersCount": 127,
  "itemsSold": 342,
  "cancelledRate": 3.5,
  "dailyRevenue": [
    {
      "date": "2024-02-08",
      "value": 1250.00
    },
    {
      "date": "2024-02-09",
      "value": 1830.50
    }
  ],
  "dailyOrders": [
    {
      "date": "2024-02-08",
      "value": 15
    },
    {
      "date": "2024-02-09",
      "value": 22
    }
  ],
  "statusDistribution": [
    {
      "name": "PENDING",
      "value": 12,
      "color": "#FFA500"
    },
    {
      "name": "PROCESSING",
      "value": 8,
      "color": "#1E90FF"
    },
    {
      "name": "SHIPPED",
      "value": 15,
      "color": "#32CD32"
    },
    {
      "name": "DELIVERED",
      "value": 88,
      "color": "#228B22"
    },
    {
      "name": "CANCELLED",
      "value": 4,
      "color": "#DC143C"
    }
  ],
  "recentOrders": [...]
}
```

---

#### 4.2 Get Inventory
Retrieve complete inventory with all product variants.

**Endpoint:** `GET /api/admin/inventory`

**Headers:**
```
Authorization: Bearer <admin-token>
```

**Response:** `200 OK`
```json
[
  {
    "productId": 1,
    "productName": "Air Max 270",
    "sku": "SKU-1-BLACK-9_0",
    "color": "Black",
    "size": 9.0,
    "stock": 25,
    "imageUrl": "https://..."
  },
  {
    "productId": 1,
    "productName": "Air Max 270",
    "sku": "SKU-1-BLACK-9_5",
    "color": "Black",
    "size": 9.5,
    "stock": 20,
    "imageUrl": "https://..."
  }
]
```

---

#### 4.3 Create Product
Add a new product to the catalog.

**Endpoint:** `POST /api/admin/products`

**Headers:**
```
Authorization: Bearer <admin-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "New Running Shoe",
  "brand": "Nike",
  "price": 160.00,
  "description": "High-performance running shoe",
  "imageUrl": "https://...",
  "category": "Running",
  "featured": true,
  "variants": [
    {
      "size": 9.0,
      "color": "Black",
      "stock": 30
    },
    {
      "size": 9.5,
      "color": "Black",
      "stock": 25
    }
  ]
}
```

**Response:** `201 Created`
```json
{
  "id": 13,
  "name": "New Running Shoe",
  "brand": "Nike",
  "price": 160.00,
  ...
}
```

---

#### 4.4 Update Product
Update an existing product.

**Endpoint:** `PUT /api/admin/products/{id}`

**Headers:**
```
Authorization: Bearer <admin-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Updated Running Shoe",
  "brand": "Nike",
  "price": 165.00,
  "description": "Updated description",
  "imageUrl": "https://...",
  "category": "Running",
  "featured": true,
  "variants": [...]
}
```

**Response:** `200 OK`

---

#### 4.5 Delete Product
Remove a product from the catalog.

**Endpoint:** `DELETE /api/admin/products/{id}`

**Headers:**
```
Authorization: Bearer <admin-token>
```

**Response:** `204 No Content`

---

#### 4.6 Get All Users
Retrieve a list of all users.

**Endpoint:** `GET /api/admin/users`

**Headers:**
```
Authorization: Bearer <admin-token>
```

**Response:** `200 OK`
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "name": "John Doe",
    "email": "john@example.com",
    "role": "CUSTOMER",
    "createdAt": "2024-01-15T10:00:00",
    "updatedAt": "2024-01-15T10:00:00"
  }
]
```

---

#### 4.7 Delete User
Remove a user from the system.

**Endpoint:** `DELETE /api/admin/users/{uuid}`

**Headers:**
```
Authorization: Bearer <admin-token>
```

**Response:** `204 No Content`

---

#### 4.8 Update Order Status
Change the status of an order.

**Endpoint:** `PATCH /api/admin/orders/{id}/status`

**Headers:**
```
Authorization: Bearer <admin-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "status": "SHIPPED"
}
```

**Valid Statuses:**
- `PENDING`
- `PROCESSING`
- `SHIPPED`
- `DELIVERED`
- `CANCELLED`

**Response:** `200 OK`
```json
{
  "id": 5,
  "userId": "...",
  "items": [...],
  "total": 150.00,
  "status": "SHIPPED",
  "shippingAddress": {...},
  "createdAt": "2024-02-15T12:00:00"
}
```

---

## Error Responses

### Standard Error Format
```json
{
  "status": 400,
  "message": "Error description",
  "timestamp": "2024-02-15T12:00:00"
}
```

### Validation Error Format
```json
{
  "status": 400,
  "errors": {
    "email": "Email should be valid",
    "password": "Password must be at least 6 characters"
  },
  "timestamp": "2024-02-15T12:00:00"
}
```

### Common HTTP Status Codes

- **200 OK**: Request successful
- **201 Created**: Resource created successfully
- **204 No Content**: Deletion successful
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Missing or invalid authentication token
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

---

## Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Get Products
```bash
curl http://localhost:8080/api/products
```

### Create Order (with token)
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [{
      "productId": 1,
      "name": "Air Max 270",
      "price": 150.00,
      "quantity": 1,
      "size": 9.0,
      "color": "Black",
      "imageUrl": "https://..."
    }],
    "shippingAddress": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zip": "10001",
      "country": "USA"
    }
  }'
```

---

## Rate Limiting

Currently, there are no rate limits implemented. Consider implementing rate limiting for production use.

## Pagination

The current API does not implement pagination. For production, consider adding pagination to list endpoints:
- `/api/products`
- `/api/admin/users`
- `/api/admin/inventory`

## CORS

The API accepts requests from:
- `http://localhost:3000`
- `http://localhost:5173`

Additional origins can be configured in `CorsConfig.java`.
