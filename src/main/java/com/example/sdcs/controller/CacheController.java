package com.example.sdcs.controller;

import com.example.sdcs.service.NodeRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CacheController {
    private final NodeRouter nodeRouter;

    @PostMapping("/")
    public ResponseEntity<?> write(@RequestBody Map<String, Object> kv) {
        kv.forEach(nodeRouter::write);
        return ResponseEntity.ok(kv);
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> get(@PathVariable String key) {
        Object value = nodeRouter.read(key);
        return (value == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(Map.of(key, value));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> delete(@PathVariable String key) {
        return ResponseEntity.ok(nodeRouter.delete(key));
    }
}
