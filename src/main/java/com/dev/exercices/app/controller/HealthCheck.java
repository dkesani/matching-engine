package com.dev.exercices.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health Check Controller.
 */
@RestController
public class HealthCheck {

    @RequestMapping("/health")
    String health() {
      return "OK";
    }
}
