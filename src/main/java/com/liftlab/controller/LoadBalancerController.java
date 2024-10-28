
package com.liftlab.controller;

import com.liftlab.service.LoadBalancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/load-balancer")
public class LoadBalancerController {

    @Autowired
    private LoadBalancerService loadBalancerService;

    @GetMapping("/{path}")
    public ResponseEntity<String> handleGetRequest(@PathVariable String path) {
        String response = loadBalancerService.forwardRequest(HttpMethod.GET, "/" + path, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{path}")
    public ResponseEntity<String> handlePostRequest(@PathVariable String path, @RequestBody Object requestBody) {
        String response = loadBalancerService.forwardRequest(HttpMethod.POST, "/" + path, requestBody);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{path}")
    public ResponseEntity<String> handlePutRequest(@PathVariable String path, @RequestBody Object requestBody) {
        String response = loadBalancerService.forwardRequest(HttpMethod.PUT, "/" + path, requestBody);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{path}")
    public ResponseEntity<String> handleDeleteRequest(@PathVariable String path) {
        String response = loadBalancerService.forwardRequest(HttpMethod.DELETE, "/" + path, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/server")
    public String addServer(@RequestParam String url) {
        loadBalancerService.addServer(url);
        return "Server added.";
    }

    @DeleteMapping("/server")
    public String removeServer(@RequestParam String url) {
        loadBalancerService.removeServer(url);
        return "Server removed.";
    }
}
