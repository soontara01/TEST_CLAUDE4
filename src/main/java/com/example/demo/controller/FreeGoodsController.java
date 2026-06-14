package com.example.demo.controller;

import com.example.demo.model.FreeGoods;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/freegoods")
public class FreeGoodsController {

    private final Map<Long, FreeGoods> freeGoods = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public FreeGoodsController() {
        FreeGoods sample = new FreeGoods(idCounter.incrementAndGet(), "FG-0001", "Sample Free Good", 10);
        freeGoods.put(sample.getId(), sample);
    }

    // GET /api/freegoods - returns all free goods
    @GetMapping
    public List<FreeGoods> getFreeGoods() {
        return List.copyOf(freeGoods.values());
    }

    // POST /api/freegoods - creates a new free good
    @PostMapping
    public ResponseEntity<FreeGoods> createFreeGoods(@RequestBody FreeGoods item) {
        item.setId(idCounter.incrementAndGet());
        freeGoods.put(item.getId(), item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
