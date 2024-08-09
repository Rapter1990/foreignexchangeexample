package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Configuration class named {@link OpenApiConfig} for OpenAPI documentation.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Sercan Noyan Germiyanoğlu",
                        url = "https://github.com/Rapter1990/foreignexchangeexample/"
                ),
                description = "Case Study - Simple Foreign Exchange Application",
                title = "foreignexchangeexample",
                version = "1.0.0"
        )
)
public class OpenApiConfig {

}
