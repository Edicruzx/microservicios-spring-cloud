package com.mitocode.microservices.product_service.expose.web;

import com.mitocode.microservices.product_service.model.dto.ProductDTO;
import com.mitocode.microservices.product_service.service.ProductService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Product Service Microservice",
                version = "0.0.1",
                description = "Modulo del ecosistema de arquitectura de Microservicios para la gestion de los productos",
                contact = @Contact(name = "Mitocode", url = "https://github.com/mitocode", email = "springcloud@mitocodenetwork.com"),
                license = @License(name = "Some license", url = "https://github.com/jchoy8890")
        ),
        servers = {@Server(url = "http://localhost:9001"), @Server(url = "http://localhost:9011")},
        tags = {@Tag(name = "ProductService", description = "Microservicio para la gestion de productos")}
)
@SecurityScheme(name = "mitocode", type = SecuritySchemeType.HTTP, scheme = "basic")
public class ProductController {

    private final ProductService productService;

    @Operation(
            description = "Endpoint que lista los productos de la Base de Datos - Sin filtro",
            tags = {"ProductService"},
            responses = {
                    @ApiResponse(
                            description = "Response OK",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))
                    ),
                    @ApiResponse(description = "Response Error", responseCode = "500", content = @Content(mediaType = "plain/text"))
            },
            security = @SecurityRequirement(name = "mitocode")
    )
    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestHeader(value = "appCallerName", required = false) String appCallerName
    ) throws InterruptedException {
        log.info("Caller Name: {}", appCallerName);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(description = "Endpoint que obtiene un producto por ID", tags = {"ProductService"}, security = @SecurityRequirement(name = "mitocode"))
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductById(
            @RequestHeader(value = "appCallerName", required = false) String appCallerName,
            @PathVariable String productId
    ) {
        log.info("Caller Name: {}", appCallerName);
        log.info("Product ID: {}", productId);
        return ResponseEntity.ok(productService.getProductById(productId));
    }
    @Operation(description = "Endpoint que guarda el producto en la Base de Datos", tags = {"ProductService"}, security = @SecurityRequirement(name = "mitocode"))
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> saveProduct(
            @RequestHeader(value = "appCallerName", required = false) String appCallerName,
            @Valid @RequestBody ProductDTO productDTO
    ) {
        log.info("Caller Name: {}", appCallerName);
        log.info("Product: {}", productDTO);
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @Operation(description = "Endpoint que actualiza un producto en la Base de Datos", tags = {"ProductService"}, security = @SecurityRequirement(name = "mitocode"))
    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestHeader(value = "appCallerName", required = false) String appCallerName,
            @PathVariable String productId,
            @Valid @RequestBody ProductDTO productDTO
    ) {
        log.info("Caller Name: {}", appCallerName);
        log.info("Product ID: {}", productId);
        log.info("Product: {}", productDTO);
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO));
    }

    @Operation(description = "Endpoint que elimina un producto en la Base de Datos", tags = {"ProductService"}, security = @SecurityRequirement(name = "mitocode"))
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @RequestHeader(value = "appCallerName", required = false) String appCallerName,
            @PathVariable String productId
    ) {
        log.info("Caller Name: {}", appCallerName);
        log.info("Product ID: {}", productId);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
