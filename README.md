# simple-payment-app 

## OpenAPI documentation

springdoc-openapi-ui is a library that helps you automatically generate and 
expose OpenAPI documentation for your Spring Boot APIs. 
It integrates with Spring Boot and exposes an interactive Swagger UI 
to visualize and interact with your REST APIs.

When application is up and running swagger UI will be available at the following URL: 
'http://localhost:8080/swagger-ui.html'

Here you can:

* View all your API endpoints.
* Test API methods (GET, POST, PUT, DELETE, etc.) with the appropriate request data.
* See example responses and status codes for each API.

## Local setup

1. Start docker containers from `local-setup` folder: 
    ``` shell
    cd local-setup
    docker compose up
    ```
1. Start `PaymentsApplication` with `local` spring profile
