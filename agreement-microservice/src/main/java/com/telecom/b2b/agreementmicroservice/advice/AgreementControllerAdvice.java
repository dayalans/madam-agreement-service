package com.telecom.b2b.agreementmicroservice.advice;

import com.telecom.b2b.agreementmicroservice.entity.ErrorMessages;
import com.telecom.b2b.agreementmicroservice.exception.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
@ControllerAdvice
public class AgreementControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest webRequest){
    String errorDescription = exception.getLocalizedMessage();
    if(errorDescription==null) errorDescription=exception.toString();
        ErrorMessages errorMessages=  new ErrorMessages(new Date(),errorDescription);
        return new ResponseEntity<>(errorMessages,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value={CustomerNotFoundException.class})
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception, WebRequest webRequest){
        String errorDescription = exception.getLocalizedMessage();
        if(errorDescription==null) errorDescription=exception.toString();
        ErrorMessages errorMessages=  new ErrorMessages(new Date(),errorDescription);
        return new ResponseEntity<>(errorMessages,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
