package com.example.sdcs.controller;

import com.example.sdcs.service.NodeRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequiredArgsConstructor
public class CacheController {
    private final NodeRouter nodeRouter;

    @PostMapping("/")
    public ResponseEntity<?> write(@RequestBody Map<String, Object> kv) {
        log.info("write content:{}", kv);
        kv.forEach(nodeRouter::write);
        return ResponseEntity.ok(kv);
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> get(@PathVariable String key) {
        log.info("get content by key:{}", key);
        Object value = nodeRouter.read(key);
        return (value == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(Map.of(key, value));
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> delete(@PathVariable String key) {
        log.info("delete content by key:{}", key);
        return ResponseEntity.ok(nodeRouter.delete(key));
    }
}
