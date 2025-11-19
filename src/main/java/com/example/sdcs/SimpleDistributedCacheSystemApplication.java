package com.example.sdcs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SimpleDistributedCacheSystemApplication {

    public static void main(String[] args) {
        log.info("SimpleDistributedCacheSystemApplication start");
        SpringApplication.run(SimpleDistributedCacheSystemApplication.class, args);
    }

}
