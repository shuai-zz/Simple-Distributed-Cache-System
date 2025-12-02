package com.example.sdcs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CacheService {
    private final Map<String,Object> data=new ConcurrentHashMap<>();

    public void put(String key,Object value){
        data.put(key,value);
        log.info("Cache updated, current size: {}", data.size());
    }

    public Object get(String key){
        Object value = data.get(key);
        log.debug("Retrieved key: {}, value: {}", key, value);
        return value;
    }

    public int delete(String key){
        var result = data.remove(key)==null?0:1;
        log.info("Cache deleted, key: {}, success: {}, current size: {}", key, result == 1, data.size());
        return result;
    }
}
