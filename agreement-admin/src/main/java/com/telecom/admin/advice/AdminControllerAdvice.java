package com.telecom.admin.advice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.telecom.admin.entity.ErrorMessages;
import com.telecom.admin.entity.FileUploadResponseMessage;
import com.telecom.admin.exception.PricePlanAlreadyExistsException;
import com.telecom.admin.exception.SubscriptionNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class AdminControllerAdvice {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest webRequest) {
		String errorDescription = exception.getLocalizedMessage();
		if (errorDescription == null)
			errorDescription = exception.toString();
		ErrorMessages errorMessages = new ErrorMessages(new Date(), errorDescription);
		return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { SubscriptionNotFoundException.class })
	public ResponseEntity<Object> handleCustomerNotFoundException(SubscriptionNotFoundException exception,
			WebRequest webRequest) {
		String errorDescription = exception.getLocalizedMessage();
		if (errorDescription == null)
			errorDescription = exception.toString();
		ErrorMessages errorMessages = new ErrorMessages(new Date(), errorDescription);
		return new ResponseEntity<>(errorMessages, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleRequestBody(MethodArgumentNotValidException ex) {

		List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
		String errorMessage = errorList.stream()
				.map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage()).sorted()
				.collect(Collectors.joining(", "));
		log.info("errorMessage : {} ", errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<FileUploadResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new FileUploadResponseMessage("File too large!"));
	}

	@ExceptionHandler(value={PricePlanAlreadyExistsException.class})
    public ResponseEntity<Object> handlePricePlanAlreadyExistsException(PricePlanAlreadyExistsException exception, WebRequest webRequest){
        String errorDescription = exception.getLocalizedMessage();
        if(errorDescription==null) errorDescription=exception.toString();
        ErrorMessages errorMessages=  new ErrorMessages(new Date(),errorDescription);
        return new ResponseEntity<>(errorMessages,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    
}
}
