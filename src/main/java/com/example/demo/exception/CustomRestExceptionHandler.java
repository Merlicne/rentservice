package com.example.demo.exception;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.model.response.ErrorResponseBody;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseBody> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException_.class)
    public ResponseEntity<ErrorResponseBody> handleUnauthorizedException(UnAuthorizedException_ ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseBody> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.FORBIDDEN.value(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseBody> handleInternalException(InternalException ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // default
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> handleAllException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
