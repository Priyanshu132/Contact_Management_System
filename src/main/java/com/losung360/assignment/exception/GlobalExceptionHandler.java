package com.losung360.assignment.exception;

import com.losung360.assignment.responseDto.Response;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateData.class)
    public ResponseEntity<Response> handleDuplicateDataExeptions(DuplicateData exception, HandlerMethod handlerMethod ) {
        return new ResponseEntity<>(new Response(true,exception.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response> handleNullPointerException(NullPointerException exception) {
        return new ResponseEntity<>(new Response(true,exception.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InValidPhoneNumber.class)
    public ResponseEntity<Response> handleInvalidValueException(InValidPhoneNumber exception) {
        return new ResponseEntity<>(new Response(true,exception.getMessage(),null), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Response> handleMalformedJwtException(MalformedJwtException exception) {
        return new ResponseEntity<>(new Response(true,exception.getMessage(),null), HttpStatus.FORBIDDEN);
    }

}




