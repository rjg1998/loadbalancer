package com.liftlab.strategy;
import com.liftlab.model.BackendServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ConditionalOnProperty(name = "loadbalancer.strategy", havingValue = "roundRobin", matchIfMissing = true)
public class RoundRobinStrategy implements LoadBalancingStrategy {
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public BackendServer selectServer(List<BackendServer> servers) {
        int idx = Math.abs(index.getAndIncrement() % servers.size());
        return servers.get(idx);
    }
}

    