package com.dev.exercices.app.service;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Optional;
import java.util.Set;
import java.util.Queue;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

import org.springframework.stereotype.Service;

import com.dev.exercices.app.constants.OrderStatus;
import com.dev.exercices.dto.Order;
import com.dev.exercices.dto.OrderBook;

@Service
public class OrderManagerService implements OrderManager {

  private static final Queue<Order> buyOrders = new PriorityQueue<>(comparing(Order::getPrc).reversed());

  private static final Queue<Order> sellOrders = new PriorityQueue<>(comparing(Order::getPrc));

  @Override
  public OrderBook getOrderBook() {
    //Using List storage, since the Iteration on Priority Queue, doe not guarantee the order of elements.
    List<Order> buys =  new ArrayList<>(buyOrders); buys.sort(comparing(Order::getPrc).reversed());
    List<Order> sells =  new ArrayList<>(sellOrders); sells.sort(comparing(Order::getPrc));
    return new OrderBook.Builder().buys(buys).sells(sells).build();
  }

  @Override
  public OrderStatus placeOrder(final Order order, OrderType type) {
   validate(order);
   return match(order,type);
  }

  private boolean validate(Order order) {
    Set<ConstraintViolation<Order>> violations =  Validation.buildDefaultValidatorFactory().getValidator()
        .validate(order);
    if(violations.isEmpty()) return true;
    else throw new ConstraintViolationException(violations);
  }

  private boolean addOrder(final Order orderToAdd, final Order head, OrderType type) {
    return OrderType.BUY.equals(type) ? head.getPrc().compareTo(orderToAdd.getPrc()) > 0
        : (OrderType.SELL.equals(type) ? head.getPrc().compareTo(orderToAdd.getPrc()) < 0 : false);
  }

  private OrderStatus match(final Order order, OrderType type) {
    Order head;
    Order orderToAdd = order;
    while((head = getQToCompare(type).peek()) != null) {
      if(addOrder(orderToAdd, head, type)) {
        getQToAdd(type).offer(orderToAdd);
        return OrderStatus.PENDING;
      }
      else if(head.getQty() < orderToAdd.getQty()) {
        //Partially filled.. continue to peek next item in the Q.
        orderToAdd = orderToAdd.withQty(orderToAdd.getQty() - getQToCompare(type).poll().getQty());
        continue;
      }
      else if(head.getQty() > orderToAdd.getQty()) {
        Order polledOrder = getQToCompare(type).poll();
        final int leftOverInQ = polledOrder.getQty() - orderToAdd.getQty();
        Optional.of(polledOrder).ifPresent(e ->
            getQToCompare(type).offer(polledOrder.withQty(leftOverInQ)));
        return OrderStatus.FILLED;
      }
      else {
        getQToCompare(type).poll();
        return OrderStatus.FILLED;
      }
    }
    //If there are no elements in the head, either because the Q is originally empty to start with or we have consumed
    // all the elements from the Q and the request is partially filled.
    if(head == null) {
      getQToAdd(type).offer(orderToAdd);
      return OrderStatus.PENDING;
    }
    return OrderStatus.REJECTED;
  }

  private static Queue<Order> getQToAdd(OrderType type) {
    if(OrderType.BUY.equals(type))
      return buyOrders;
    else
      return sellOrders;
  }

  private static Queue<Order> getQToCompare(OrderType type) {
    if(OrderType.SELL.equals(type))
      return buyOrders;
    else
      return sellOrders;
  }
}
