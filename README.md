# Mall — Cloud-Native E-Commerce Platform

> **Work in progress.** This project is under active development. Features, APIs, and infrastructure details are subject to change.

A full-stack e-commerce platform built with a microservices architecture on Spring Boot 3 / Spring Cloud Alibaba. The system handles everything from product catalog management and inventory to orders, memberships, and coupons, backed by a Vue-based admin UI and deployed on AWS.

---

## Table of Contents

- [Distributed Architecture Fundamentals](#distributed-architecture-fundamentals)
- [Core Tech Stack](#core-tech-stack)
- [Environment Setup](#environment-setup)
- [Development Standards](#development-standards)
- [Modules](#modules)
- [Getting Started](#getting-started)

---

## Distributed Architecture Fundamentals

Services register with **Nacos** for service discovery and pull configuration from the Nacos config center. Cross-service calls use **OpenFeign**. All external traffic enters through a single **Spring Cloud Gateway** instance.

```
renren-fast-vue  (Admin UI — Vue 2)
       │
       ▼
mall-gateway  (Spring Cloud Gateway — port 8088)
       │
       ├── mall-product    (Catalog: categories / brands / attributes / SPU / SKU)
       ├── mall-coupon     (Promotions: coupons / seckill / full-reduction)
       ├── mall-member     (Members: users / addresses)
       ├── mall-order      (Orders: lifecycle management)
       ├── mall-warehouse  (Warehouse: inventory / purchase orders)
       └── mall-third-party (Object storage: AWS S3 presigned upload)

mall-common      (Shared: R response / exceptions / validation / Transfer Objects)
renren-fast      (Admin auth backend — port 8080)
renren-generator (MyBatis-Plus code generator)
```

---

## Core Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4 |
| Microservices | Spring Cloud 2024 + Spring Cloud Alibaba 2023 |
| ORM | MyBatis-Plus |
| Database | MySQL 8 |
| Service Registry / Config | Nacos 2.x |
| Remote Calls | OpenFeign |
| API Gateway | Spring Cloud Gateway |
| Object Storage | AWS S3 (presigned URL direct upload — course equivalent of Alibaba OSS) |
| Admin Frontend | Vue 2 + renren-fast-vue (component-based) |
| Infrastructure | AWS EC2 + Terraform |

---

## Environment Setup

| Tool | Purpose |
|---|---|
| Vagrant + Linux | Local virtualized development environment |
| Docker | Run middleware (MySQL, Redis, Nacos) in containers |
| MySQL 8 | One database per service: `mall_pms` / `mall_sms` / `mall_ums` / `mall_oms` / `mall_wms` |
| Redis | Cache layer (integration in progress) |
| renren-generator | Reverse-engineer database tables into MyBatis-Plus CRUD boilerplate |
| renren open source | `renren-fast` (admin backend) + `renren-fast-vue` (admin frontend) as scaffolding base |

---

## Development Standards

### Data Validation — JSR 303

`jakarta.validation` annotations declaratively validate request body fields. Custom groups (`AddGroup` / `UpdateGroup` / `UpdateStatusGroup`) separate validation rules for create vs. update operations.

```java
// Custom enum-value constraint (mall-common)
@ListValue(vals = {0, 1}, groups = {AddGroup.class})
private Integer showStatus;
```

### Global Exception Handling

`MallExceptionControllerAdvice` (`@RestControllerAdvice`) centrally catches `MethodArgumentNotValidException` and any uncaught `Throwable`, mapping them to standardized error responses using `BizCodeEnume`.

### Unified Response Wrapper

Every endpoint returns `R` (defined in `mall-common`):

```json
{ "code": 0, "msg": "success", "data": { ... } }
```

### Global Cross-Origin (CORS)

`GlobalCorsConfig` in `mall-gateway` handles CORS at the gateway level — downstream services need no individual `@CrossOrigin` configuration.

### Enum Status & Business Error Codes

`BizCodeEnume` centralizes all business error codes, eliminating magic numbers across services:

```java
UNKNOW_EXCEPTION(10000, "Unknown error"),
VAILD_EXCEPTION (10001, "Validation failed")
```

### VO / TO / PO Separation

| Type | Role |
|---|---|
| **PO** (Entity) | Maps 1-to-1 with a database table; managed by MyBatis-Plus |
| **TO** (Transfer Object) | Carries data between services over Feign; lives in `mall-common` (`SpuBoundTo`, `SkuReductionTo`, …) |
| **VO** (View Object) | Request / response shapes for the frontend; defined per business module |

### Logical Delete

MyBatis-Plus `@TableLogic` marks records as deleted without physically removing them. Queries automatically filter soft-deleted rows.

```java
// CategoryEntity.java
@TableLogic(value = "1", delval = "0")
private Integer showStatus;
```

### Lombok

`@Data` generates getters, setters, `equals`, `hashCode`, and `toString`. `@Slf4j` injects a `log` field — no boilerplate in any class.

---

## Modules

### `mall-product`
Product catalog service. Manages the full catalog tree:
- Multi-level category hierarchy (3 levels)
- Brands and brand-category associations
- Attribute groups, base attributes, and sale attributes
- SPU (Standard Product Unit) and SKU (Stock Keeping Unit) full lifecycle
- Product image galleries and rich-text descriptions

### `mall-coupon`
Promotions engine. Coupon rules, seckill events, full-reduction and tiered-discount strategies.

### `mall-member`
Member service. User registration, login, and address book.

### `mall-order`
Order service. Order creation, status transitions, and order item details.

### `mall-warehouse`
Warehouse service. Warehouse management, supplier / purchase order management, receiving, and stock levels.

### `mall-third-party`
Integration service. Generates AWS S3 presigned URLs so the frontend can upload files directly to S3 without routing through the application backend.

### `mall-gateway`
Single entry point. Path rewriting, Nacos load-balanced routing to downstream services, and global CORS configuration.

### `mall-common`
Shared library (not a runnable service). Contains `R` response wrapper, `BizCodeEnume`, JSR 303 validation groups and custom constraints (`@ListValue`), and Feign Transfer Objects (`SpuBoundTo`, `SkuReductionTo`, `MemberPrice`).

### `renren-fast` + `renren-fast-vue`
Open-source admin scaffolding (renren-security) providing authentication and a Vue 2 management UI. Extended with mall-specific business logic.

### `renren-generator`
Code generation tool that reverse-engineers database tables into MyBatis-Plus CRUD skeletons.

---

## Getting Started

### Prerequisites

- Java 17, Maven 3.9+
- MySQL 8 — databases: `mall_pms`, `mall_sms`, `mall_ums`, `mall_oms`, `mall_wms`
- Nacos 2.x on `localhost:8848`
- Redis on `localhost:6379`
- Node.js 14+ (for the admin frontend)

### Start a Service

```bash
# Example: start the product service
cd mall-product
mvn spring-boot:run
```

Each service reads its datasource and Nacos address from `src/main/resources/application.yml`. Update those values to match your local environment before starting.

### Start the Admin Frontend

```bash
cd renren-fast-vue
npm install
npm run dev
```

The frontend proxies all API calls through `mall-gateway` on port `88`.

---

## Project Status

- [x] Product categories / brands / attribute system
- [x] SPU / SKU save flow (with Feign calls to coupon and warehouse services)
- [x] Warehouse basics (warehouses, suppliers, purchase orders)
- [x] Global exception handling / unified response / CORS / JSR 303 validation
- [ ] SPU publish (on-shelf) flow
- [ ] Member authentication & JWT integration
- [ ] Complete order lifecycle
- [ ] Coupon rule engine
