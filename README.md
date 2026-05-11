# Mall — Cloud-Native E-Commerce Platform

> **Work in progress.** This project is under active development. Features, APIs, and infrastructure details are subject to change.

A full-stack e-commerce platform built with a microservices architecture on Spring Boot 3 / Spring Cloud Alibaba. The system handles everything from product catalog management and inventory to orders, memberships, and coupons, backed by a Vue-based admin UI and deployed on AWS.

---

## Architecture Overview

The platform is split into independently deployable Spring Boot services that communicate through a Nacos service registry and are fronted by a Spring Cloud Gateway.

```
renren-fast-vue  (Admin UI - Vue 2)
       │
       ▼
mall-gateway  (Spring Cloud Gateway — port 8088)
       │
       ├── mall-product    (Product catalog, categories, attributes, SPU/SKU)
       ├── mall-coupon     (Promotions, coupons, seckill)
       ├── mall-member     (User accounts, addresses)
       ├── mall-order      (Order lifecycle)
       ├── mall-warehouse  (Inventory, warehouses, purchase orders)
       └── mall-third-party (AWS S3 presigned URL uploads)

mall-common   (Shared utilities, base entities, R response wrapper)
renren-fast   (Authentication & admin backend — port 8080)
renren-generator (MyBatis-Plus code generator)
```

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4, Spring Cloud 2024, Spring Cloud Alibaba 2023 |
| ORM | MyBatis-Plus |
| Database | MySQL 8 |
| Service Discovery | Nacos |
| API Gateway | Spring Cloud Gateway |
| Object Storage | AWS S3 (presigned URL flow) |
| Admin Frontend | Vue 2 + renren-fast-vue |
| Infrastructure | AWS EC2 / VPC — provisioned with Terraform |

---

## Modules

### `mall-product`
Core product domain. Manages the full catalog tree:
- Category hierarchy (multi-level)
- Brands and brand-category relations
- Attribute groups and attributes (sale vs. base attributes)
- SPU (Standard Product Unit) and SKU (Stock Keeping Unit) lifecycle
- Product image galleries and descriptions

### `mall-coupon`
Promotions engine. Covers coupon rules, seckill events, full-reduction and tiered-discount strategies.

### `mall-member`
Member management. User registration, login, address book.

### `mall-order`
Order management. Order creation, status transitions, and order item details.

### `mall-warehouse`
Inventory service. Warehouses, purchase orders, receiving, and stock levels.

### `mall-third-party`
Integration service. Generates AWS S3 presigned URLs so the frontend can upload files directly to S3 without routing through the backend.

### `mall-gateway`
Single entry point for all API traffic. Rewrites paths and routes requests to the correct downstream service via Nacos load-balanced URIs.

### `mall-common`
Shared library (not a runnable service). Contains base entity classes, the `R` response wrapper, common exceptions, and validation utilities.

### `renren-fast` + `renren-fast-vue`
Open-source admin scaffolding used as the back-office backend and frontend. Extended with mall-specific business logic.

### `renren-generator`
Code generation tool for bootstrapping new CRUD modules from the database schema.

---

## Infrastructure

Terraform configurations under [`Terraform/`](Terraform/) provision AWS resources including VPC, subnets, security groups, and EC2 instances for running the middleware stack (MySQL, Redis, Nacos).

---

## Getting Started

### Prerequisites

- Java 17
- Maven 3.9+
- MySQL 8 (database names: `mall_pms`, `mall_sms`, `mall_ums`, `mall_oms`, `mall_wms`)
- Nacos 2.x running on `localhost:8848`
- Node.js 14+ (for the frontend)

### Run a Service

```bash
# Start the product service (example)
cd mall-product
mvn spring-boot:run
```

Each service reads its datasource and Nacos address from `src/main/resources/application.yml`. Update those values to match your local environment before starting.

### Run the Admin Frontend

```bash
cd renren-fast-vue
npm install
npm run dev
```

The frontend proxies API calls through `mall-gateway` on port `8088`.

---

## Project Status

This project is actively being developed. Current focus areas:

- [ ] Complete SPU/SKU publish flow
- [ ] Warehouse and inventory integration with orders
- [ ] Coupon and promotion rule engine
- [ ] Member authentication and JWT integration
- [ ] Full Terraform deployment pipeline

Contributions and feedback are welcome.
