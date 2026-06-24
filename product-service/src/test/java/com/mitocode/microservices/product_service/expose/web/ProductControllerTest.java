package com.mitocode.microservices.product_service.expose.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // mockMvc te permite llamar a tus endpoints sin necesidad de conectar a http

    @Test
    @Order(3)
    @DisplayName("List products")
    void when_call_list_products_then_return_status_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .header("appCallerName", "postman"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Microservicios Avanzados")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
   // @DisabledOnJre(JRE.JAVA_17)
   // @DisabledOnOs(OS.MAC)
    @Order(2)
    @DisplayName("List Products Not Found")
    void when_call_list_products_then_return_status_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(1)
    @DisplayName("Save Product")
    void when_call_save_product_then_return_status_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveProduct")
                        .header("appCallerName", "postman")
                        .content("{\n" +
                                "    \"productId\": \"P00003\",\n" +
                                "    \"productName\": \"Microservicios Avanzados II\",\n" +
                                "    \"productType\": \"Curso\",\n" +
                                "    \"price\": 750,\n" +
                                "    \"stock\": 50\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Microservicios Avanzados")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}