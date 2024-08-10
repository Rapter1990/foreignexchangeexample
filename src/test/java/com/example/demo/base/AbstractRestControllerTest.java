package com.example.demo.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for integration tests of REST controllers.
 *
 * This class is annotated with {@link SpringBootTest} to load the full application context for integration tests.
 * It also uses {@link AutoConfigureMockMvc} to configure MockMvc for performing HTTP requests in tests.
 * The class provides {@link MockMvc} and {@link ObjectMapper} as protected fields for use in test subclasses.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AbstractRestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

}
