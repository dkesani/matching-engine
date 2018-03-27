package com.dev.exercices.dto;

import java.math.BigDecimal;

import javax.validation.constraints.*;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Abstract Representation of an Order.
 * No property has been created for storing a Symbol/Identifier, as the samples did not
 * have one.
 */
@Value.Immutable(prehash = true)
@Value.Style(typeImmutable = "*", builder = "new")
@JsonSerialize(as = Order.class)
@JsonDeserialize(as = Order.class)
public interface AbstractOrder {

  @Min(value = 1, message = "Qty should be greater than 0.")
  @Max(value = 100000, message = "Qty should be less than 100001.")
  int getQty();

  @NotNull
  @DecimalMin("0.01")
  @DecimalMax("100000.00")
  @Digits(integer = 6, fraction = 2)
  BigDecimal getPrc();
}
