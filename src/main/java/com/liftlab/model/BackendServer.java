
package com.liftlab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackendServer {
    private String url;
    private boolean isHealthy = true;

    public BackendServer(String url) {
        this.url = url;
    }
}
    