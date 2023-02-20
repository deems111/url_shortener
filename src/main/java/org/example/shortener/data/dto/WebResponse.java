package org.example.shortener.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Basic response for all web requests
 */
@Data
public class WebResponse<T> {

    /**
     * Use only if an error occurs
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorText;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public WebResponse(String errorText) {
        this.errorText = errorText;
    }

    public WebResponse(T data) {
        this.data = data;
    }
}
