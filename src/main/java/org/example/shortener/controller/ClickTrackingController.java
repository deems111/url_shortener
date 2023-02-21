package org.example.shortener.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.shortener.data.dto.ClickTrackingDto;
import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.enums.Sorting;
import org.example.shortener.service.ClickTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/clicks")
@AllArgsConstructor
@Tag(name = "Controller for managing clicks on URL")
public class ClickTrackingController {
    private final ClickTrackingService clickTrackingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get by short Url Id", description = "Returns all Clicks by short Url Id (shortenerId) ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - ShortenerId Id incorrect")
    })
    public WebResponse<List<ClickTrackingDto>> getByShortUrlId(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                               @RequestParam UUID shortenerId, @RequestParam int pageNum,
                                                               @RequestParam  @Valid @Max(100) int pageSize, @RequestParam Sorting sort) {
        return new WebResponse<>(clickTrackingService.findByShortenerId(new Locale(localeStr), shortenerId, pageNum, pageSize, sort));
    }
}
