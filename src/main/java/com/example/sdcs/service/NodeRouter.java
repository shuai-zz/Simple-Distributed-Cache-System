package com.example.sdcs.service;

import com.example.sdcs.config.NodeConfig;
import com.example.sdcs.util.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NodeRouter {
    private final NodeConfig nodeConfig;
    private final CacheService cacheService;
    private final WebClient client = WebClient.builder().build();

    private int findNode(String key) {
        return HashUtil.hashToIndex(key, nodeConfig.getNodes().size());
    }

    private boolean isLocal(int nodeIndex) {
        return nodeIndex == nodeConfig.getNodeId();
    }

    private String getNodeBaseUrl(int nodeIndex) {
        var result="http://" + nodeConfig.getNodes().get(nodeIndex);
        log.info("nodeBaseUrl:{}", result);
        return result;
    }

    public Object write(String key, Object value) {
        int nodeIndex = findNode(key);
        log.info("write key:{},value:{},nodeIndex:{}", key, value, nodeIndex);
        if (isLocal(nodeIndex)) {
            cacheService.put(key, value);
            return value;
        } else {
            return client.post()
                    .uri(getNodeBaseUrl(nodeIndex) + "/")
                    .bodyValue(Map.of(key, value))
                    .retrieve().bodyToMono(Object.class).block();
        }
    }

    public Object read(String key) {
        int nodeIndex = findNode(key);
        log.info("read key:{},nodeIndex:{}", key, nodeIndex);
        if (isLocal(nodeIndex)) {
            return cacheService.get(key);
        } else {
            var result= client.get()
                    .uri(getNodeBaseUrl(nodeIndex) + "/" + key)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            if(result!=null) return result.get(key);
            return null;
        }
    }

    public int delete(String key) {
        int nodeIndex = findNode(key);
        Integer result;
        log.info("delete key:{},nodeIndex:{}", key, nodeIndex);
        if (isLocal(nodeIndex)) {
            result = cacheService.delete(key);
        } else {
            result = client.delete()
                    .uri(getNodeBaseUrl(nodeIndex) + "/" + key)
                    .retrieve().bodyToMono(Integer.class)
                    .block();
        }
        return result == null ? 0 : result;
    }

}
