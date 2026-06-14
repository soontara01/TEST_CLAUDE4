package com.example.demo.controller;

import com.example.demo.model.Promotion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final Map<Long, Promotion> promotions = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public PromotionController() {
        Promotion sample = new Promotion(idCounter.incrementAndGet(), "PRD-0001", "FG-0001", 1, 1);
        promotions.put(sample.getId(), sample);
    }

    // GET /api/promotions - returns all promotions
    @GetMapping
    public List<Promotion> getPromotions() {
        return List.copyOf(promotions.values());
    }

    // POST /api/promotions - creates a new promotion
    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        promotion.setId(idCounter.incrementAndGet());
        promotions.put(promotion.getId(), promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

    // DELETE /api/promotions/{id} - deletes a promotion by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        if (promotions.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // GET /api/promotions/{id}/apply?quantity=N - returns the free quantity earned for the purchased quantity
    @GetMapping("/{id}/apply")
    public ResponseEntity<Integer> applyPromotion(@PathVariable Long id, @RequestParam int quantity) {
        Promotion promotion = promotions.get(id);
        if (promotion == null) {
            return ResponseEntity.notFound().build();
        }
        int freeQuantity = (quantity / promotion.getBuyQuantity()) * promotion.getGetQuantity();
        return ResponseEntity.ok(freeQuantity);
    }
}
