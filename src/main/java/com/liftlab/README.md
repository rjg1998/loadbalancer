# Load Balancer

This project implements a simple load balancer that distributes HTTP requests among a set of backend servers using a configurable load balancing strategy. The default strategy is round-robin, but other strategies (e.g., random selection) are also supported.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Setup Instructions](#setup-instructions)
3. [High-Level Call Flow Diagram](#high-level-call-flow-diagram)
4. [Low-Level Call Flow Diagram](#low-level-call-flow-diagram)
5. [Extending or Scaling the Implementation](#extending-or-scaling-the-implementation)

## Prerequisites

- Java 17 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA, Eclipse)
- Lombok (for reducing boilerplate code)

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd loadbalancer
   ```

2. **Install Dependencies**:
   Use Maven to install the project dependencies:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   You can run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
   By default, the load balancer will listen on port `8080`. You can change this in the `application.properties` file.

4. **Register Backend Servers**:
   You can configure backend servers in the `application.properties` file:
   ```properties
   loadbalancer.servers=http://backend1.com,http://backend2.com
   loadbalancer.strategy=round-robin  # or random
   ```

5. **Send Requests**:
   You can send requests to the load balancer, and it will forward them to the backend servers based on the selected strategy. Use tools like Postman or curl for testing.

## High-Level Call Flow Diagram

![High-Level Call Flow Diagram](./diagrams/high_level_call_flow.png)

**Description**: This diagram illustrates the overall flow of how requests are received by the load balancer, processed, and forwarded to backend servers.

## Low-Level Call Flow Diagram

![Low-Level Call Flow Diagram](./diagrams/low_level_call_flow.png)

**Description**: This diagram provides a detailed view of the interactions between components within the load balancer, including request handling, server selection, and error management.

## Extending or Scaling the Implementation

### Adding New Load Balancing Strategies

To add a new load balancing strategy:

1. **Create a New Strategy Class**:
   Implement the `LoadBalancingStrategy` interface in a new class, e.g., `LeastConnectionsStrategy`.

   ```java
   public class LeastConnectionsStrategy implements LoadBalancingStrategy {
       // Implement the logic to select a server based on the least connections.
   }
   ```

2. **Register the New Strategy**:
   Add the new strategy to your configuration class and make sure it is conditionally loaded based on the property.

3. **Update Configuration**:
   Update the `application.properties` to include the new strategy.

### Health Check Mechanism

To implement a health check mechanism for the backend servers:

1. **Create a Health Check Task**:
   Use a scheduled task to periodically check the health of each backend server.

2. **Modify Server Registration**:
   Update the server registration logic to temporarily remove unhealthy servers from the load balancer's list.

### Scaling the Application

- **Increase Backend Servers**: You can add more backend servers in the configuration to handle more requests.
- **Horizontal Scaling**: Deploy multiple instances of the load balancer behind a reverse proxy or use a cloud-based load balancing service for high availability.
- **Caching**: Implement caching strategies for frequently requested data to reduce load on backend servers.

