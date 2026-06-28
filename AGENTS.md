## Agent Quickstart Checklist

- Read this file and the `HELP.md` before making changes.
- Understand authentication: see `src/main/resources/application.yml`, `JwtService`, `JwtAuthenticationFilter`, `FlashCartSecurityConfig`.
- Use Maven wrapper for build/run/test: `./mvnw spring-boot:run`, `./mvnw clean package`, `./mvnw test`.

## Big-picture architecture

- Monolithic Spring Boot application. Main packages: `auth`, `user`, `driver`, `product`, `order`, `store` under `com.flashcart.flashcart_backend`.
- Controllers are thin HTTP adapters -> Services hold business logic -> Repositories handle JPA persistence. DTOs and MapStruct mappers are used to translate between entities and DTOs.
- Security: JWT-based auth. Tokens created by `JwtService` and validated in `JwtAuthenticationFilter`. Security filter chain is configured in `security/FlashCartSecurityConfig` and method-level access control uses `@PreAuthorize` annotations (not URL-based permissions).

## Important files to read first

- `src/main/resources/application.yml` — contains DB, JPA and `jwt.secret` / `jwt.expiration` settings.
- `src/main/java/com/flashcart/flashcart_backend/security/JwtService.java` — token generation/validation.
- `src/main/java/com/flashcart/flashcart_backend/security/JwtAuthenticationFilter.java` — how tokens are extracted and Authentication is set.
- `src/main/java/com/flashcart/flashcart_backend/security/FlashCartSecurityConfig.java` — filter chain; note `anyRequest().permitAll()` and reliance on `@PreAuthorize`.
- Example controller patterns: `auth/controller/AuthController.java` (base `/api/v1/auth`), `product/controller/ProductController.java` (`/products`), `order/controller/OrderController.java` (`/api/v1/orders`), `store/controller/StoreController.java` (`/stores`).

## Project-specific conventions & patterns

- Method-level security is authoritative: look for `@PreAuthorize("hasRole('...')")` on controller methods (e.g. `ProductController#create` requires `STORE_OWNER`). Do not rely on URL patterns in `FlashCartSecurityConfig`.
- DTO + Mapper pattern: each module uses `*RequestDTO`, `*ResponseDTO`, and a MapStruct `*Mapper` annotated with `@Mapper(componentModel = "spring")` (see `product/mapper/ProductMapper.java`). Generated mappers live under `target/generated-sources/annotations`.
- Services perform ownership/validation checks (e.g. `ProductService` validates store ownership before create/update; `OrderService` decrements stock and generates tracking id).
- Exceptions are centralized in `exception/` and handled by `GlobalExceptionHandler` which maps domain exceptions to HTTP statuses.
- Packaging: use Maven wrapper in repo root; MapStruct and Lombok are used (see `pom.xml`) so IDE annotation processors should be enabled for local development.

## Build / run / debug

- Start locally with the Maven wrapper: `./mvnw spring-boot:run` (reads `application.yml`).
- Build an executable jar: `./mvnw clean package` -> run `java -jar target/*.jar`.
- Run tests: `./mvnw test`.
- If changing generated code (MapStruct), run a full `clean package` or enable annotation processing in IDE.

## Auth flow examples (use these when writing integration tests or calling endpoints)

- Login
  - POST /api/v1/auth/login
  - Body: `{ "email": "user@example.com", "password": "secret" }` (see `auth/dto/LoginRequestDTO.java`)
  - Response token at `token` field (see `auth/dto/LoginResponseDTO.java`).

- Using the token
  - Add header: `Authorization: Bearer <token>`
  - Example: create a product (requires STORE_OWNER role):
    curl -X POST http://localhost:8080/products \
      -H "Authorization: Bearer <token>" -H "Content-Type: application/json" \
      -d '{...product payload...}'

## Integration points & external deps to watch

- PostgreSQL configured in `application.yml`. Tests may assume a running DB unless a test profile overrides it.
- JWT dependency: `io.jsonwebtoken` (see `pom.xml` and `JwtService`). `jwt.secret` is required; prefer overriding via environment variables for CI.
- MapStruct (codegen) and Lombok (compile-time) are used — ensure annotation processing is enabled in CI/IDE.

## Quick troubleshooting hints

- If requests seem unauthenticated despite valid tokens: confirm `JwtAuthenticationFilter` is registered (see `FlashCartSecurityConfig`) and that token uses the `jwt.secret` from the active configuration.
- If mapping methods are missing at compile time: run `./mvnw clean package` to regenerate MapStruct sources.
- Look at `GlobalExceptionHandler` for how domain exceptions map to HTTP codes (useful when writing tests asserting error responses).

## Where to add new endpoints/features

- Follow existing package structure: controller -> service -> repository -> mapper -> dto -> entity.
- Add role checks with `@PreAuthorize` on controller methods. Reuse `JwtService` & existing user/role model for auth.

---
Reference: main entry `src/main/java/com/flashcart/flashcart_backend/FlashcartBackendApplication.java` and `HELP.md` for additional notes.

