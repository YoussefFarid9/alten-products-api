# Alten Products API

Spring Boot **3.5.5** backend for Alten products trial project.

##  Features
- **JWT authentication** (`/api/auth/account`, `/api/auth/token`)
- **Admin-only rule**: only `admin@admin.com` can create/update/delete products
- **Products** CRUD with JPA
- Per-user **Cart** and **Wishlist**
- **H2 database** (file-based) for easy dev
- Spring Data JPA, Spring Security (OAuth2 Resource Server + JOSE), Validation, Lombok
- Optional Swagger UI for docs

---

##  Getting Started

### Prerequisites
- Java 21
- Maven 3.9+
- Git

### Clone & Build
```bash
git clone https://github.com/YoussefFarid9/alten-products-api.git
cd alten-products-api
mvn clean install


### Run Using
mvn spring-boot:run
