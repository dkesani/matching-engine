package com.dev.exercices.app.controller;


import static com.dev.exercices.app.service.OrderManager.OrderType.BUY;
import static com.dev.exercices.app.service.OrderManager.OrderType.SELL;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.dev.exercices.app.constants.OrderStatus;
import com.dev.exercices.app.service.OrderManager;
import com.dev.exercices.dto.Order;
import com.dev.exercices.dto.OrderBook;

@RestController
public class OrderController {

  @Inject
  OrderManager orderManager;

  @GetMapping(value = "/book")
  OrderBook book(){
    return orderManager.getOrderBook();
  }

  // This should technically return 201 HttpStatus with the URL link to the object created.
  @PostMapping("/buy")
  ResponseEntity<OrderStatus> buy(@RequestBody @NotNull Order order) {
    return ResponseEntity.ok(orderManager.placeOrder(order, BUY));
  }

  // This should technically return 201 HttpStatus with the URL link to the object created.
  @PostMapping("/sell")
  ResponseEntity<OrderStatus> sell(@RequestBody @NonNull Order order) {
    return ResponseEntity.ok(orderManager.placeOrder(order, SELL));
  }
}
