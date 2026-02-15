# Quick Start Guide

Get SoleStride backend up and running in 5 minutes!

## Prerequisites Check

Before starting, make sure you have:
- [ ] Java 21 installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] Docker installed (`docker --version`)

## Step-by-Step Setup

### 1. Start Database (1 minute)

```bash
# Navigate to the project directory
cd solestride-backend

# Start MySQL container
docker-compose up -d

# Verify it's running
docker-compose ps
```

Expected output:
```
NAME                  STATUS
solestride-mysql      Up
```

### 2. Build Application (2 minutes)

```bash
# Clean and build the project
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests
- Create the JAR file

### 3. Run Application (1 minute)

```bash
# Start the Spring Boot application
mvn spring-boot:run
```

Wait for the message:
```
Started SoleStrideApplication in X.XXX seconds
```

### 4. Verify Installation (1 minute)

Open a new terminal and test the API:

```bash
# Test public endpoint
curl http://localhost:8080/api/products

# Should return JSON array of products
```

## Default Test Accounts

### Admin Account
```
Email: admin@solestride.com
Password: admin123
```

### Customer Account
```
Email: customer@solestride.com
Password: customer123
```

## Quick API Tests

### 1. Login as Customer
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer@solestride.com",
    "password": "customer123"
  }'
```

Save the token from the response!

### 2. Get Your Orders
```bash
# Replace YOUR_TOKEN with the token from login
curl http://localhost:8080/api/orders \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 3. View Products
```bash
curl http://localhost:8080/api/products
```

### 4. Login as Admin
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@solestride.com",
    "password": "admin123"
  }'
```

### 5. View Dashboard Stats (Admin)
```bash
# Replace ADMIN_TOKEN with admin's token
curl http://localhost:8080/api/admin/stats \
  -H "Authorization: Bearer ADMIN_TOKEN"
```

## Connect Your Frontend

Update your frontend `.env` file:

```env
VITE_API_URL=http://localhost:8080
```

Or in your React app:
```javascript
const API_BASE_URL = 'http://localhost:8080';
```

## Troubleshooting

### Port Already in Use

If port 8080 is busy:

1. Stop the conflicting service, OR
2. Change the port in `application.properties`:
   ```properties
   server.port=8081
   ```

### Database Connection Failed

```bash
# Stop and restart MySQL
docker-compose down
docker-compose up -d

# Check logs
docker-compose logs mysql
```

### Application Won't Start

```bash
# Check Java version (must be 21)
java -version

# Clean build
mvn clean install -DskipTests

# Try again
mvn spring-boot:run
```

## Next Steps

1. **Read the API Documentation**: Check `API_DOCUMENTATION.md` for all endpoints
2. **Explore the Code**: Start with `SoleStrideApplication.java`
3. **Test with Postman**: Import the endpoints into Postman
4. **Integrate Frontend**: Connect your React app to the API

## Useful Commands

```bash
# Stop the application
Ctrl + C

# Stop MySQL
docker-compose down

# View application logs
mvn spring-boot:run

# Rebuild after code changes
mvn clean install

# Run tests only
mvn test

# Package as JAR
mvn package
```

## Common Issues

### "Table doesn't exist"
- Solution: The app auto-creates tables. Wait for startup to complete.

### "Access denied for user"
- Solution: Check `application.properties` credentials match `docker-compose.yml`

### "JWT token expired"
- Solution: Login again to get a new token (tokens expire after 24 hours)

## Development Tips

1. **Hot Reload**: The app has Spring DevTools - code changes trigger auto-restart
2. **Database Viewer**: Use tools like MySQL Workbench or DBeaver to view data
3. **API Testing**: Use Postman, Insomnia, or Thunder Client for VS Code
4. **Logging**: Check console output for detailed SQL queries and errors

## Production Deployment

For production:

1. Change JWT secret in `application.properties`
2. Use environment variables for sensitive data
3. Enable HTTPS
4. Set up proper database (not Docker for dev)
5. Configure CORS for your production frontend domain
6. Add rate limiting
7. Enable production logging

## Support

- Check `README.md` for detailed documentation
- Review `API_DOCUMENTATION.md` for endpoint details
- Open an issue on GitHub for problems

Happy coding! 🚀
