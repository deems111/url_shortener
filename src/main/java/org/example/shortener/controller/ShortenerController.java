package org.example.shortener.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.shortener.data.dto.ShortUrlDto;
import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.data.request.DisableRequest;
import org.example.shortener.data.request.ModifyRequest;
import org.example.shortener.data.request.ShortenerRequest;
import org.example.shortener.service.ShortenerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/shortener")
@AllArgsConstructor
@Tag(name = "Controller for shorting URL")
public class ShortenerController {
    private final ShortenerService shortenerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Convert URL to short URL", description = "Returns short URL using CRC algorithm and saves in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved in DB"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    public WebResponse<ShortUrlDto> addShortUrl(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                @Valid @RequestBody ShortenerRequest request) {
        return new WebResponse<>(shortenerService.addUrl(new Locale(localeStr), request));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get full URL from short URL", description = "Returns full URL from short URl")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully operation"),
            @ApiResponse(responseCode = "404", description = "Not found - The short URL was not found")
    })
    public WebResponse<ShortUrlDto> getOriginalUrl(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                   @RequestParam @Parameter(description = "User id", example = "5461685e-b06b-11ed-afa1-0242ac120002", required = true) UUID userId,
                                                   @RequestParam @Parameter(description = "Short Url String", example = "https://localhost:8080/qwerty", required = true) String shortUrl) {
        return new WebResponse<>(shortenerService.getOriginalUrl(new Locale(localeStr), userId, shortUrl));
    }

    @PostMapping("disable")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Disable URL", description = "Disable URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Disabled"),
            @ApiResponse(responseCode = "404", description = "Not found - The URL or User was not found")
    })
    public WebResponse<Boolean> disableUrl(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                           @RequestBody @Valid DisableRequest request) {
        shortenerService.disableUrl(new Locale(localeStr), request);
        return new WebResponse<>(true);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Modify URL", description = "Modifies short URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified"),
            @ApiResponse(responseCode = "404", description = "Not found - The URL or User was not found")
    })
    public WebResponse<ShortUrlDto> modify(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                       @RequestBody @Valid ModifyRequest request) {
        return new WebResponse<>(shortenerService.modifyShortUrl(new Locale(localeStr), request));
    }

    @GetMapping("/getByUser")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all URL by user", description = "Returns all URL, saved by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all by user"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    public WebResponse<List<ShortUrlDto>> getByUser(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                    @RequestParam @NotBlank @Parameter(required = true) UUID userId) {
        return new WebResponse<>(shortenerService.findUrlAllByUser(new Locale(localeStr), userId));
    }

    @GetMapping("/getByDate")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all URL by date", description = "Returns all URL in date period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all by date"),
            @ApiResponse(responseCode = "404", description = "Not found - Date incorrect")
    })
    public WebResponse<List<ShortUrlDto>> getByPeriod(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                      @RequestParam @NonNull @Parameter(description = "Start Date of Searching", required = true)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                      @RequestParam @NonNull @Parameter(description = "End Date of Searching", required = true)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {
        return new WebResponse<>(shortenerService.findUrlAllByPeriod(new Locale(localeStr), dateFrom, dateTo));
    }

    @GetMapping("count")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get count of url", description = "Returns count of url by user with / without disabled url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully counted"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    public WebResponse<Integer> getCountOfUrlByUser(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                      @RequestParam UUID userId, @RequestParam Boolean countDisabled) {
        return new WebResponse<>(shortenerService.getCountOfUrlByUser(new Locale(localeStr), userId, countDisabled));
    }
}
