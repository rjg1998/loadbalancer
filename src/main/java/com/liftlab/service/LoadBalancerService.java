
package com.liftlab.service;

import com.liftlab.model.BackendServer;
import com.liftlab.strategy.LoadBalancingStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadBalancerService {

    @Autowired
    LoadBalancingStrategy strategy;

    @Getter
    private final List<BackendServer> servers = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public void addServer(String url) {
        servers.add(new BackendServer(url));
    }

    public void removeServer(String url) {
        servers.removeIf(server -> server.getUrl().equals(url));
    }

    public String forwardRequest(HttpMethod method, String requestPath, Object requestBody) {
        if (requestPath == null || requestPath.isEmpty()) {
            throw new IllegalArgumentException("Request path must not be null or empty");
        }
        BackendServer server = strategy.selectServer(servers);
        if (server == null) throw new RuntimeException("No available servers");

        String url = server.getUrl() + requestPath;
        HttpEntity<Object> entity = new HttpEntity<>(requestBody);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Handle 4xx errors (client errors)
            System.err.println("Client error occurred: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Client error occurred while forwarding request: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            // Handle 5xx errors (server errors)
            System.err.println("Server error occurred: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Server error occurred while forwarding request: " + e.getMessage(), e);
        } catch (RestClientException e) {
            // Handle other errors (e.g., connection errors)
            System.err.println("Error occurred while forwarding request: " + e.getMessage());
            throw new RuntimeException("Error occurred while forwarding request: " + e.getMessage(), e);
        }
    }
}
    