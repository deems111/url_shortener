package org.example.shortener.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.shortener.data.dto.UserDto;
import org.example.shortener.data.dto.WebResponse;
import org.example.shortener.data.request.UserAddRequest;
import org.example.shortener.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "Controller for managing user data")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add user", description = "Adds user (registration), returns userId and request params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved in DB"),
            @ApiResponse(responseCode = "409", description = "Duplicate Email - user with Email already exists"),
    })
    public WebResponse<UserDto> addUser(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                        @RequestBody @Valid UserAddRequest request) {
        var result = userService.addUser(new Locale(localeStr), request);
        return new WebResponse<>(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get User", description = "Returns user data by User UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    public WebResponse<UserDto> getUser(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                        @RequestParam UUID userId) {
        return new WebResponse<>(userService.getUser(new Locale(localeStr), userId));
    }

    @GetMapping("getEmails")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get User Emails", description = "Returns user emails using pages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved in DB"),
            @ApiResponse(responseCode = "404", description = "Not found - The user was not found")
    })
    public WebResponse<List<String>> getUserEmails(@RequestHeader(name = "Accept-Language", required = false) String localeStr,
                                                   @RequestParam int pageNum, @RequestParam int pageSize) {
        return new WebResponse<>(userService.getUserEmails(new Locale(localeStr), pageNum, pageSize));
    }

}
