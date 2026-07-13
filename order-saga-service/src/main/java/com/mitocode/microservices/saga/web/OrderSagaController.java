package com.mitocode.microservices.saga.web;

import com.mitocode.microservices.saga.model.CreateOrderRequest;
import com.mitocode.microservices.saga.model.OrderSaga;
import com.mitocode.microservices.saga.service.OrderSagaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sagas")
public class OrderSagaController {
    private final OrderSagaService service;
    public OrderSagaController(OrderSagaService service) { this.service = service; }
    @PostMapping public ResponseEntity<OrderSaga> start(@Valid @RequestBody CreateOrderRequest request) { return ResponseEntity.accepted().body(service.start(request)); }
    @GetMapping("/{id}") public OrderSaga get(@PathVariable String id) { return service.get(id); }
    @PostMapping("/{id}/compensate") public ResponseEntity<OrderSaga> compensate(@PathVariable String id) { return ResponseEntity.accepted().body(service.compensate(id)); }
}
