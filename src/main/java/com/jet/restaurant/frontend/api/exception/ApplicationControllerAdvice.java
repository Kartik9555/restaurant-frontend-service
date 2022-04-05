package com.jet.restaurant.frontend.api.exception;

import com.jet.restaurant.frontend.api.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorDTO> handleException(Throwable ex) {
        ErrorDTO error = new ErrorDTO("500", "Internal Server Error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
