package com.dev.exercices.dto;

import java.util.List;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable(prehash = true)
@Value.Style(typeImmutable = "OrderBook", builder = "new")
@JsonSerialize(as = OrderBook.class)
@JsonDeserialize(as = OrderBook.class)
public interface AsbtractOrderBook {

  List<Order> buys();

  List<Order> sells();
}
