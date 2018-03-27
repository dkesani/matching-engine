package com.dev.exercices.dto;

import org.immutables.value.Value;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable(prehash = true)
@Value.Style(typeImmutable = "*", builder = "new")
@JsonSerialize(as = OrderError.class)
@JsonDeserialize(as = OrderError.class)
public interface AbstractOrderError {

  String message();

  HttpStatus statusCode();
}
