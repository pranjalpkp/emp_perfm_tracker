# System Design

### 1. Scaling for 500 Concurrent Managers Running Reports

500 concurrent users is manageable for a Spring Boot application, but 500 concurrent **analytical queries** can quickly overwhelm the database. In this scenario, the main bottleneck is usually the **database layer**, not the application servers.

First, the application should be deployed with **multiple instances behind a load balancer**. Since the Spring Boot application is stateless, horizontal scaling allows requests to be distributed across several instances, improving throughput and fault tolerance.

However, scaling the application servers alone won't solve the problem because heavy reporting queries can still saturate the primary database. To address this, I would introduce **PostgreSQL read replicas** and route reporting endpoints to the replicas while keeping all write operations (like submitting reviews or updating goals) on the primary database. This separation ensures that long-running analytical queries do not block transactional workloads.

Another important consideration is **connection pooling**. By default, HikariCP uses a pool size of 10, which can be exhausted quickly under concurrent reporting traffic. Increasing the pool size for read operations and configuring separate pools for read and write data sources ensures efficient database connection management.

If some reports take longer than a few seconds to generate (for example large CSV exports), it is better not to keep the HTTP request open. Instead, the request can be offloaded to a **background job queue** (such as RabbitMQ or Kafka). The report can be generated asynchronously and uploaded to storage (e.g., S3), and the manager can receive an email or notification with a download link once the report is ready.

---

### 2. Optimizing `GET /cycles/{id}/summary` for 100k+ Reviews

Currently the `getCycleSummary()` endpoint runs multiple aggregation queries (`AVG`, `COUNT`, etc.). With 100k+ reviews, repeatedly running these queries can cause expensive table scans and significantly increase database latency.

The first step is to add **proper indexing** on the columns used for filtering and aggregation. For example:

```sql
CREATE INDEX idx_reviews_cycle_rating 
ON performance_reviews_tbl(review_cycle_id, rating);

CREATE INDEX idx_goals_cycle_status 
ON goals_tbl(review_cycle_id, status);