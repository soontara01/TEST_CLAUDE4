package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public ProductController() {
        Product sample = new Product(idCounter.incrementAndGet(), "P-0001", "Sample Product", 19.99);
        products.put(sample.getId(), sample);
    }

    // GET /api/products - returns all products
    @GetMapping
    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    // POST /api/products - creates a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(idCounter.incrementAndGet());
        products.put(product.getId(), product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
