package com.lifefitness.employee.exception;

import org.springframework.http.ResponseEntity;

import com.lifefitness.employee.entity.ApiError;

public class ResponseEntityBuilder {
	public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
