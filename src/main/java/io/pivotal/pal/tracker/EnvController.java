package io.pivotal.pal.tracker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {

    @GetMapping("/env")
    public String getEnv(@Value("${PORT:NOT_SET}")  String port,
                         @Value("${MEMORY_LIMIT:NOT_SET}")  String memory,
                         @Value("${CF_INSTANCE_INDEX:NOT_SET}")  String index,
                        @Value("${CF_INSTANCE_ADDR:NOT_SET}")  String address) {
        return "port: " + port;
    }
}
