package com.mitocode.microservices.product_service.expose.web;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("GET /product devuelve los productos")
    void getAllProductsReturnsProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product()));

        mockMvc.perform(get("/product").header("appCallerName", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productId").value("P00003"))
                .andExpect(jsonPath("$[0].productName").value("Microservicios Avanzados II"));
    }

    @Test
    @DisplayName("Una ruta inexistente devuelve 404")
    void unknownRouteReturnsNotFound() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /product guarda un producto válido")
    void saveProductReturnsSavedProduct() throws Exception {
        when(productService.saveProduct(any(ProductDTO.class))).thenReturn(product());

        mockMvc.perform(post("/product")
                        .header("appCallerName", "test")
                        .content("""
                                {
                                  "productId": "P00003",
                                  "productName": "Microservicios Avanzados II",
                                  "productType": "Curso",
                                  "price": 750,
                                  "stock": 50
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("P00003"))
                .andExpect(jsonPath("$.port").value(9001));
    }

    @Test
    @DisplayName("POST /product valida el cuerpo de la petición")
    void saveInvalidProductReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/product")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ProductDTO product() {
        return ProductDTO.builder()
                .productId("P00003")
                .productName("Microservicios Avanzados II")
                .productType("Curso")
                .price(750L)
                .stock(50)
                .port(9001)
                .build();
    }
}
