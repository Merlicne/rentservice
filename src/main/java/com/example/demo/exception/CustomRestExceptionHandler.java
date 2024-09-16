package com.example.demo.exception;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.model.response.ResponseBody;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseBody> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ResponseBody(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseBody> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ResponseBody(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseBody> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(new ResponseBody(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseBody> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(new ResponseBody(HttpStatus.FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ResponseBody> handleInternalException(InternalException ex) {
        return new ResponseEntity<>(new ResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // default
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody> handleAllException(Exception ex) {
        // return new ResponseEntity<>(new ResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage().substring(0, 100)), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new ResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
