package org.example.shortener.controller.advice;

import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.exception.DuplicateException;
import org.example.shortener.exception.NotFoundException;
import org.springframework.http.HttpStatus;
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
}
