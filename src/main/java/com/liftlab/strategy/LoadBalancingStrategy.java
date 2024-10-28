
package com.liftlab.strategy;

import com.liftlab.model.BackendServer;
import java.util.List;

public interface LoadBalancingStrategy {
    BackendServer selectServer(List<BackendServer> servers);
}
    