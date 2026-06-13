package com.example.demo.controller;

import com.example.demo.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final Map<Long, Item> items = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public ItemController() {
        Item sample = new Item(idCounter.incrementAndGet(), "Sample Item", 9.99);
        items.put(sample.getId(), sample);
    }

    // GET /api/items - returns all items
    @GetMapping
    public List<Item> getItems() {
        return List.copyOf(items.values());
    }

    // POST /api/items - creates a new item
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        item.setId(idCounter.incrementAndGet());
        items.put(item.getId(), item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
