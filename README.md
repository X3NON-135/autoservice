![](src/main/resources/assets/auto-service-image.png?raw=true "Auto service logo")
<div align="center">
    <h2>AutoService is online application for clients, who are concerning to look after their machine in reliable state
        <br> and for workers of service stations to maintain reports of their clients</h2>
</div>

# Functionality
* App simplify job for workers to do casual paper work, such as
    * Register new employed Masters
    * Calculate Master's salary
    * Create new Order for client
    * Save client's autos and his orders
    * Add new products
    * Add new service
# App Structure
Based on N-Tier architecture:
1. Layer of Controllers.
2. Layer of Service.
3. Layer of Repository.
# Technologies
* Java 11
* Tomcat 9.0.65
* Maven
* Spring Boot 2.7.5
# Quick start
1) Clone this repository
2) Set up necessary fields in ```application.properties```
```
spring.datasource.url=DB_URL
spring.datasource.username=USER
spring.datasource.password=PASSWORD
spring.jpa.properties.hibernate.dialect=DIALECT
```
3) Start maven compiling code and packaging it to jar - run **mvn clean package**
4) Run project
# How to send HTTP methods
* Open web-browser and write down ```http://localhost:8080/swagger-ui/#``` to open Swagger
# General overview
* Discount person on products = number of his orders * 1%
* Discount person for services = number of orders * 2%
* Master’s salary = 40% of the cost of the service he was engaged in
* When calculating and issuing a salary to a master, the status of the service changes to «paid»
* When changing the order status to «successfully completed» or «unsuccessful completed», completion date equals to current date
* Diagnostics is a service. If you agree to repair this service is carried out free of charge, in case of refusal of a person from repair this service costs 500 UAH
