package com.example.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.models.product;
import com.example.backend.repository.productRepository;

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origin:http://localhost:5173}")
public class productController {

    private final productRepository prod;

    public productController(productRepository prod) {
        this.prod = prod;
    }

    @GetMapping("/products")
    public List<product> getProducts() {
        return prod.findAll();
    }

    @GetMapping("/products/{id}")
    public product getProduct(@PathVariable int id) {
        return prod.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @PostMapping("/products")
    public ResponseEntity<product> postProduct(@RequestBody product product) {
        product.setId(0);
        product saved = prod.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/products/{id}")
    public product updateProduct(@PathVariable int id, @RequestBody product updatedProduct) {
        product existing = prod.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        existing.setName(updatedProduct.getName());
        existing.setCost(updatedProduct.getCost());
        return prod.save(existing);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if (!prod.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        prod.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
