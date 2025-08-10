# eco-wa-service



\# WhatsApp E-Commerce Platform (Java Spring Boot + Saleor)



\## Overview

This platform enables a full e-commerce experience inside WhatsApp using the \*\*WhatsApp Business API\*\* and the \*\*Saleor\*\* GraphQL backend.  

It follows a \*\*microservices architecture\*\* for modularity, scalability, and ease of deployment.



---



\## Architecture



&nbsp;        ┌───────────────────┐

&nbsp;        │  WhatsApp User    │

&nbsp;        └───────┬───────────┘

&nbsp;                │

&nbsp;                ▼

&nbsp;       ┌───────────────────────┐

&nbsp;       │ whatsapp-service       │

&nbsp;       │ (Webhook/API handler)  │

&nbsp;       └───────┬────────────────┘

&nbsp;                │

&nbsp;                ▼

&nbsp;       ┌───────────────────────┐

&nbsp;       │ conversation-service   │

&nbsp;       │ (Session + chatbot)    │

&nbsp;       └───────┬────────────────┘

&nbsp;   ┌───────────┼────────────────┐

&nbsp;   ▼           ▼                ▼



saleor-adapter payment-service notification-service

(GraphQL+cache) (Payments) (Order updates)

│

▼

Saleor Backend





---



\## Services



\### 1. `whatsapp-service`

\- \*\*Role:\*\* Entry point for WhatsApp API callbacks (inbound messages) and outbound messages.

\- \*\*Key Responsibilities:\*\*

&nbsp; - Verify webhook requests from Meta using `WH\_VERIFY\_TOKEN`.

&nbsp; - Parse incoming messages and send them to `conversation-service`.

&nbsp; - Send outbound messages to customers using the WhatsApp Business Cloud API.

\- \*\*Tech stack:\*\* Spring Boot WebFlux / RestTemplate (for API calls).

\- \*\*Ports:\*\* 8080



---



\### 2. `conversation-service`

\- \*\*Role:\*\* Orchestrates chatbot flows and manages user sessions.

\- \*\*Key Responsibilities:\*\*

&nbsp; - State management (current step in ordering process).

&nbsp; - Communicating with `saleor-adapter` to fetch products.

&nbsp; - Calling `payment-service` when checkout is triggered.

&nbsp; - Sending final status updates to `notification-service`.

\- \*\*Tech stack:\*\* Spring Boot + Redis (optional for session store).

\- \*\*Ports:\*\* 8081



---



\### 3. `saleor-adapter-service`

\- \*\*Role:\*\* Interface between internal microservices and Saleor GraphQL API.

\- \*\*Key Responsibilities:\*\*

&nbsp; - Optimized GraphQL queries to fetch products, categories, orders.

&nbsp; - Optional caching with Redis for faster product search.

&nbsp; - Abstracting Saleor's schema from other services.

\- \*\*Tech stack:\*\* Spring Boot WebFlux (GraphQL client), Redis.

\- \*\*Ports:\*\* 8082



---



\### 4. `payment-service`

\- \*\*Role:\*\* Handles payment link generation and status verification.

\- \*\*Key Responsibilities:\*\*

&nbsp; - Integrating with payment gateways (Stripe, Razorpay, etc.).

&nbsp; - Generating payment links and sending them back via `conversation-service`.

&nbsp; - Verifying payment completion and notifying order system.

\- \*\*Tech stack:\*\* Spring Boot REST client.

\- \*\*Ports:\*\* 8083



---



\### 5. `notification-service`

\- \*\*Role:\*\* Sends order updates and tracking information to users via WhatsApp.

\- \*\*Key Responsibilities:\*\*

&nbsp; - Sending order confirmation, shipment updates, delivery notifications.

&nbsp; - Scheduling follow-up messages if needed.

\- \*\*Tech stack:\*\* Spring Boot REST client.

\- \*\*Ports:\*\* 8084



---



\## How Services Communicate



\- All inter-service communication is \*\*HTTP REST\*\* over internal Docker network.

\- Example:

&nbsp; - `whatsapp-service` receives incoming message → sends POST request to `conversation-service`.

&nbsp; - `conversation-service` calls `saleor-adapter-service` for product data.

&nbsp; - `conversation-service` calls `payment-service` for payment links.

&nbsp; - `notification-service` sends outbound WhatsApp messages for updates.



---



\## Local Development



\### Prerequisites

\- Java 17+

\- Maven 3.8+

\- Docker \& Docker Compose

\- WhatsApp Business API credentials from Meta

\- Saleor backend running (local or cloud)

\- (Optional) Redis for caching/session store



\### Running with Docker Compose

```bash

docker-compose up --build



This will start:



All 5 microservices



Redis (optional)



Saleor backend (if configured locally)







cd whatsapp-service

mvn spring-boot:run





You can run each service independently if pointing to the correct service URLs in application.yml.



Environment Variables

Variable	Description	Example

WH\_VERIFY\_TOKEN	Token to verify WhatsApp webhook	my\_verify\_token

WHATSAPP\_API\_TOKEN	Meta API token for sending messages	EAAB...

SALEOR\_API\_URL	URL of Saleor GraphQL endpoint	https://saleor.example.com/graphql/

PAYMENT\_GATEWAY\_KEY	Payment gateway API key	

REDIS\_HOST	Redis hostname	redis

REDIS\_PORT	Redis port	6379

DATABASE\_URL	PostgreSQL connection string	postgresql://user:pass@host/dbname





Deploying to Render

Create a Render service for each microservice.



Set environment variables for each service in Render dashboard.



Render automatically sets:



RENDER\_GIT\_BRANCH → Git branch deployed



RENDER\_GIT\_COMMIT → Commit hash deployed



Use Docker-based deploy for consistent builds.



Enhancing the System

Add AI Chatbot: Integrate OpenAI or Rasa into conversation-service.



Add Inventory Sync: Extend saleor-adapter-service to update stock from Saleor to WhatsApp catalog.



Payment Webhooks: Add webhook listener in payment-service for instant payment updates.



Multi-language support: Store user’s language preference in conversation-service.





