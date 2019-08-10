package com.xenosgrilda.app.exceptions.handlers;

import com.xenosgrilda.app.exceptions.CustomerEmptyBodyException;
import com.xenosgrilda.app.exceptions.CustomerExceptionResponse;
import com.xenosgrilda.app.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;

@ControllerAdvice
public class CustomerControllerExceptionHandler {

    private ZoneId zoneId;

    @PostConstruct
    public void setUpLocalDate() {
        this.zoneId = ZoneId.of("America/Sao_Paulo");
    }

    // CustomerNotFound
    @ExceptionHandler
    public ResponseEntity<CustomerExceptionResponse> handleCustomerException(CustomerNotFoundException exc) {

        // Creating the response based on "CustomerExceptionResponse"
        CustomerExceptionResponse response = new CustomerExceptionResponse();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exc.getMessage());
        response.setTimeStamp(LocalDate.now(this.zoneId).toString());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Jackson will convert it to JSON!
    }

    // CustomerEmptyBody
    @ExceptionHandler
    ResponseEntity<CustomerExceptionResponse> handleCustomerEmptyException(CustomerEmptyBodyException exc){

        // Creating the response body
        CustomerExceptionResponse response = new CustomerExceptionResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exc.getMessage());
        response.setTimeStamp(LocalDate.now(this.zoneId).toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Since this exceptions accepts "Exception", in parameter, it will be treated as a generic exception handler
    @ExceptionHandler
    public ResponseEntity<CustomerExceptionResponse> handleBadRequestException(Exception exc) {

        CustomerExceptionResponse response = new CustomerExceptionResponse();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exc.getMessage());
        response.setTimeStamp(LocalDate.now(this.zoneId).toString());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

/**
 * @ControllerAdvice : Specialization of @Component for classes that declare @ExceptionHandler, @InitBinder, or
 * @ModelAttribute methods to be shared across multiple @Controller classes.
 *
 * The @ControllerAdvice annotation was first introduced in Spring 3.2. It allows you to handle exceptions
 * across the whole application, not just to an individual controller. You can think of it as an interceptor of
 * exceptions thrown by methods annotated with @RequestMapping or one of the shortcuts.
 *
 * @ExceptionHandler : Will pickup exceptions that matches the param, in the "handleCustomerException()" it will match
 * exceptions of "CustomerNotFoundException" based on the function parameter.
 */