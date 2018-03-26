package com.dev.exercices.app.service;

import com.dev.exercices.app.constants.OrderStatus;
import com.dev.exercices.dto.Order;
import com.dev.exercices.dto.OrderBook;

/**
 * Interface for dealing with Orders(Buy,sell)
 */
public interface OrderManager {

  enum OrderType {
    BUY,
    SELL
  }

  OrderBook getOrderBook();

  OrderStatus placeOrder(Order order, OrderType orderType);
}
