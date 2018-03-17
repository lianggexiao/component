package com.qing.minisys.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qing.minisys.domain.BaseResult;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResult<String> exception(ConstraintViolationException e){
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations())
        {
            sb.append("Error: " + violation.getPropertyPath() + " : " + violation.getMessage() + "\n");

        }
//        String msg = e.getConstraintViolations().toString();
        return BaseResult.error(sb.toString());
    }


    @ExceptionHandler
    @ResponseBody
    public BaseResult<String> exception(Exception e){
        return BaseResult.error(e.getMessage());
    }
}
