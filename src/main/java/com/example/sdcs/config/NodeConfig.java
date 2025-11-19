package com.example.sdcs.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NodeConfig {
    @Getter
    @Value("${node.id}")
    private int nodeId;

    @Value("${node.port}")
    private int port;

    @Getter
    @Value("${cluster.nodes}")
    private List<String> nodes;

}
