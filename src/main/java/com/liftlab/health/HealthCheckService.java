
package com.liftlab.health;

import com.liftlab.model.BackendServer;
import com.liftlab.service.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckService {

    @Autowired
    LoadBalancerService loadBalancerService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void checkHealth() {
        for (BackendServer server : loadBalancerService.getServers()) {
            try {
                restTemplate.getForObject(server.getUrl() + "/health", String.class);
                server.setHealthy(true);
            } catch (Exception e) {
                server.setHealthy(false);
            }
        }
    }
}
    