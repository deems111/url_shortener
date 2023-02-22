package org.example.shortener.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.shortener.data.dto.ShortenerEventDto;
import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.service.ShortenerEventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Tag(name = "Controller for managing events of URL")
public class ShortenerEventController {
    private final ShortenerEventService shortenerEventService;

    @GetMapping("/getByShortUrl")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get by short Url Id", description = "Returns all URL by short Url Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all by Url Id"),
            @ApiResponse(responseCode = "404", description = "Not found - URL Id incorrect")
    })
    public WebResponse<List<ShortenerEventDto>> getByShortUrlId(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                            @RequestParam UUID shortenerId) {
        return new WebResponse<>(shortenerEventService.findByShortenerId(new Locale(localeStr), shortenerId));
    }
}
