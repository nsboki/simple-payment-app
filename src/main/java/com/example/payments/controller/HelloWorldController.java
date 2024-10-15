package com.example.payments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "HelloWorld API", description = "API to return Hello World")
public class HelloWorldController {

    @Operation(summary = "Get hello world message", description = "Returns a simple Hello World message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved message"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
