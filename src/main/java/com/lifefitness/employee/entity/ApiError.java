package com.lifefitness.employee.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
	private HttpStatus status;
	private String message;
	private Object errors;

	public ApiError( LocalDateTime timestamp,HttpStatus status, String message, Object errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
		this.timestamp= timestamp;
	}
	
	public ApiError(HttpStatus status, String message, Object errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}


	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}


}