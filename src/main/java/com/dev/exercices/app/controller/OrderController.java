package com.dev.exercices.app.controller;


import static com.dev.exercices.app.service.OrderManager.OrderType.BUY;
import static com.dev.exercices.app.service.OrderManager.OrderType.SELL;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
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

  @PostMapping("/buy")
  ResponseEntity<OrderStatus> buy(@RequestBody Order order) {

    return ResponseEntity.ok(orderManager.placeOrder(order, BUY));
  }

  @PostMapping("/sell")
  ResponseEntity<OrderStatus> sell(@RequestBody Order order) {
    return ResponseEntity.ok(orderManager.placeOrder(order, SELL));
  }
}
