package com.danielpm1982.springboot3_profile_manager.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.logging.Level;

@ControllerAdvice
@Log
public class GlobalExceptionHandler {

  @ExceptionHandler(PersonDeleteByIdFailedException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  @ResponseBody
  ErrorResponse handlePersonDeleteByIdFailedException(PersonDeleteByIdFailedException e){
    log.log(Level.WARNING, e::toString);
    return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
  }
  // this handler catches any Exception thrown, of type PersonDeleteByIdFailedException.class, and returns to the user an ErrorResponse
  // with the message from the current exception instance, after logging the toString() of the same exception to the server app console,
  // with further details. In the case of such RuntimeException, if the user tries to delete a resource and,
  // although existing at the DB, the resource ends up not being deleted, an instance of PersonDeleteByIdFailedException is thrown, at
  // PersonServiceImpl, and that instance, then, is caught here. The message of such Exception is then set at the ErrorResponse, which,
  // in turn, is sent back to the user, at the response body, with a 503 status code.

  @ExceptionHandler(PersonSaveOrUpdateByIdFailedException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  @ResponseBody
  ErrorResponse handlePersonSaveOrUpdateByIdFailedException(PersonSaveOrUpdateByIdFailedException e){
    log.log(Level.WARNING, e::toString);
    return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
  }
  // this handler catches any Exception thrown, of type PersonSaveOrUpdateByIdFailedException.class, and returns to the user an ErrorResponse
  // with the message from the current exception instance, after logging the toString() of the same exception to the server app console,
  // with further details. In the case of such RuntimeException, if the user tries to insert a resource and, although it not existing at the DB,
  // the resource ends up not being inserted, or if the user tries to update a resource and, although it already existing at the DB, the resource
  // ends up not being updated, an instance of PersonSaveOrUpdateByIdFailedException is thrown, at PersonCustomRepositoryImpl, and that instance,
  // then, is caught here. The message of such Exception is then set at the ErrorResponse, which, in turn, is sent back to the user, at the response
  // body, with a 503 status code.

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.log(Level.WARNING, e::toString);
    String errorDetailMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce((a,b)->a+". "+b).orElseGet(()->"Unknown errors")+".";
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
            "Bad Request: "+e.getClass().getSimpleName()+" ! Errors found at the content of your request body. "+errorDetailMessage);
  }
  // this handler catches MethodArgumentNotValidException, thrown by Spring itself, when the user passes a request with a body content incompatible
  // with the current @Valid configuration for the respective DTO (e.g. PersonDTO), although compatible with the Media (MIME) Type expected structure.
  // Spring can unmarshall the request body content, but the values of one or more properties are invalid, according to the application validation
  // definition. It returns, to the user, an ErrorResponse, with the type of the current exception instance and errors detected, after logging the
  // toString() of the same exception to the server app console, with further details. It is returned the 400 status code.

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    log.log(Level.WARNING, e::toString);
    return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
            "Bad Request: "+e.getClass().getSimpleName()+" ! "+e.getMessage()+". "+
                    "Request Body content incompatible with field 'accept' at Http request header, " +
                    "Media (MIME) Type. Cannot unmarshall request body content.");
  }
  // this handler catches HttpMessageNotReadableException, thrown by Spring itself, when the user passes a request with a body content incompatible
  // with field 'accept' at Http request header, Media (MIME) Type. Being impossible to unmarshall the request body content. It returns, to the user,
  // an ErrorResponse, with the auto-generated exception instance message, after logging the toString() of the same exception to the server app console,
  // with further details. It is returned the 400 status code.

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse handleGeneralException(Exception e) {
    log.log(Level.WARNING, e::toString);
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(),
            "Internal Server Error: "+e.getClass().getSimpleName()+" !");
  }
  // this handler catches any Exception thrown, not caught by other @ExceptionHandlers, and returns, to the user, an ErrorResponse, with the type
  // of the current exception instance, after logging the toString() of the same exception to the server app console, with further details. It is
  // returned the 500 status code.
}
