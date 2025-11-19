package com.example.sdcs.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private final Map<String,Object> data=new ConcurrentHashMap<>();

    public void put(String key,Object value){
        data.put(key,value);
    }
    public Object get(String key){
        return data.get(key);
    }
    public int delete(String key){
        return data.remove(key)==null?0:1;
    }
}
