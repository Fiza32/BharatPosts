package com.blog.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MyErrorDetails> userNotFoundExceptionHandler(UserNotFoundException ex, WebRequest wr){
		MyErrorDetails errorMsg = new MyErrorDetails();
		errorMsg.setTimestamp(LocalDateTime.now());
		errorMsg.setMessage(ex.getMessage());
		errorMsg.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(errorMsg, HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<MyErrorDetails> postNotFoundExceptionHandler(PostNotFoundException ex, WebRequest wr){
		MyErrorDetails errorMsg = new MyErrorDetails();
		errorMsg.setTimestamp(LocalDateTime.now());
		errorMsg.setMessage(ex.getMessage());
		errorMsg.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(errorMsg, HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentExceptionsHandler(MethodArgumentNotValidException ex){
//		MyErrorDetails errorMsg = new MyErrorDetails();
//		errorMsg.setTimestamp(LocalDateTime.now());
//		errorMsg.setMessage(ex.getMessage());
//		errorMsg.setDetails(wr.getDescription(false));
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
		
//		return new ResponseEntity<MyErrorDetails>(errorMsg, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> otherExceptionsHandler(Exception ex, WebRequest wr){
		MyErrorDetails errorMsg = new MyErrorDetails();
		errorMsg.setTimestamp(LocalDateTime.now());
		errorMsg.setMessage(ex.getMessage());
		errorMsg.setDetails(wr.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(errorMsg, HttpStatus.BAD_REQUEST);
	}
}
