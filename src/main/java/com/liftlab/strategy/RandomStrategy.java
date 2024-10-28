
package com.liftlab.strategy;

import com.liftlab.model.BackendServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@ConditionalOnProperty(name = "loadbalancer.strategy", havingValue = "random")
public class RandomStrategy implements LoadBalancingStrategy {
    private final Random random = new Random();

    @Override
    public BackendServer selectServer(List<BackendServer> servers) {
        return servers.get(random.nextInt(servers.size()));
    }
}
    