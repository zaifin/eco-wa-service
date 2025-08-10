# eco-wa-service



\# WhatsApp E-Commerce Platform (Java Spring Boot + Saleor)



\## Overview
# Multi-Service E-Commerce Integration Platform

This project implements a **microservices-based architecture** for integrating **WhatsApp Business API**, **Saleor E-Commerce**, **Payment Gateway**, and **Notification System**.  
It is designed for **scalability**, **extensibility**, and **cloud deployment** (tested with [Render](https://render.com)).

---

## 📌 High-Level Architecture

![High Level Architecture](architecture/high_level_architecture.png)

The architecture consists of several microservices communicating over HTTP (REST) and messaging (RabbitMQ).  
Key components:
- **WhatsApp Service** — Handles incoming/outgoing messages via WhatsApp Business API.
- **Conversation Service** — Manages chatbot logic and order conversation flows.
- **Saleor Adapter Service** — Integrates with Saleor GraphQL API for product, order, and customer management.
- **Payment Service** — Manages payment requests and status updates.
- **Notification Service** — Sends transactional notifications via various channels (e.g., email, push).
- **Redis** — Caching layer.
- **PostgreSQL** — Central database for persistent data.

---

## 📜 Service Overviews

### 1. **WhatsApp Service**
- Receives webhooks from Meta’s WhatsApp API.
- Validates webhook tokens.
- Sends and receives messages.
- Passes conversation context to the Conversation Service.

![Message Flow](architecture/message_flow.png)

---

### 2. **Conversation Service**
- Orchestrates chat flows.
- Calls Saleor Adapter for product/order details.
- Manages session data in Redis.

![Conversation Flow](architecture/conversation_flow.png)

---

### 3. **Saleor Adapter Service**
- Connects to Saleor's GraphQL API.
- Retrieves product catalog, stock status, and order history.
- Creates new orders when confirmed by the Conversation Service.

![Order Placement Flow](architecture/order_placement_flow.png)

---

### 4. **Payment Service**
- Creates payment links.
- Handles payment gateway callbacks.
- Updates Saleor when payment is confirmed.

![Payment Confirmation Flow](architecture/payment_confirmation_flow.png)

---

### 5. **Notification Service**
- Sends confirmation messages (email, push, WhatsApp).
- Alerts users about shipping, delivery, or payment status.

![Order Notification Flow](architecture/order_notification_flow.png)

---

### 6. **Data & Caching Layer**
- **PostgreSQL**: Persistent storage for order and transaction data.
- **Redis**: Used for caching frequently accessed data and conversation states.

![Data Storage & Caching Flow](architecture/data_storage_caching_flow.png)

---

## Local Development
### Clone the repository:
   ```bash
   git clone https://github.com/your-org/microservices-ecommerce.git
   cd microservices-ecommerce


###  Build images and start containers in the foreground
docker-compose up --build



### Verify services are running (example local ports):

WhatsApp Service → http://localhost:8080
Conversation Service → http://localhost:8081
Saleor Adapter Service → http://localhost:8082
Payment Service → http://localhost:8083
Notification Service → http://localhost:8084

### Configure environment variables

Required variables:

DATABASE_URL=jdbc:postgresql://localhost:5432/saleor_pnou
DATABASE_USERNAME=saleor
DATABASE_PASSWORD=yourpassword
WH_VERIFY_TOKEN=
WHATSAPP_API_TOKEN=
REDIS_HOST=
REDIS_PORT=
PAYMENT_GATEWAY_KEY=



### Stop and clean up

# Stop services
docker-compose down

# Stop and remove volumes (⚠ deletes all local data)
docker-compose down -v
