package org.meihuai.springmvc.test.controller;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ResponseBody
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(Throwable.class)
  public void exceptionHandler(Throwable t, HttpServletRequest request) throws Throwable {
 /*   if (AnnotationUtils.findAnnotation(t.getClass(), ResponseStatus.class) != null){
      throw t;
    }*/
    System.err.println("Request2222222 [" + request.getRequestURI() + "] from [" + request.getRemoteAddr() + "] error is " + t);

    throw t;
  }
}
