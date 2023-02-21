package org.example.shortener.controller.advice;

import org.example.shortener.aop.annotation.LogException;
import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.exception.DuplicateException;
import org.example.shortener.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public WebResponse<?> notFound(NotFoundException notFoundException) {
        return new WebResponse<>(notFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DuplicateException.class)
    public WebResponse<?> duplicate(DuplicateException duplicateException) {
        return new WebResponse<>(duplicateException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @LogException
    public WebResponse<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        var builder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            builder.append(fieldName)
                    .append(" - ").append(errorMessage).append("; ");
        });
        return new WebResponse<>(builder.toString());
    }
}
