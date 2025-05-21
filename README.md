# Lease a car APIs

There are two APIs created to provide functionalities for a car lease:
- lease-broker API
- lease-company API 

### Requirements

- Java 21
- Maven

## lease-company API

This API allows leasing company to manage car information.

### Features

- Maintain cars (get, create, update, delete).
- Sample car data and interest rates are loaded in H2-in memory database when the application starts.
- API documentation is provided in Swagger (/swagger-ui/index.html)
- Health check is provided via Actuator (/actuator/health)
- Security validation with API key. 

### How to Run

1. Clone this repository:

   ```bash
   git clone https://github.com/fliduan/lease
   cd lease-company

2. Build the project:

mvn clean install

3. Run the application:

mvn spring-boot:run

4. Access the Swagger UI:

Open your browser and go to http://localhost:8081/swagger-ui/index.html.

5. Authenticate with API key

## lease-broker API
 
This API allows brokers to manage customer information and to create a quote for a car lease for the customer.

### Features

- Maintain customers (get, create, update, delete).
- Create a quote for a car lease for the customer.
  In the quote, the lease rate is calculated based on de vehicle information, and the contract details.
- API documentation is provided in Swagger (/swagger-ui/index.html)
- Health check is provided via Actuator (/actuator/health)

### How to Run

1. Clone this repository:

   ```bash
   git clone https://github.com/fliduan/lease
   cd lease-broker

2. Build the project:

mvn clean install

3. Run the application:

mvn spring-boot:run

4. Access the Swagger UI:

Open your browser and go to http://localhost:8080/swagger-ui/index.html.
