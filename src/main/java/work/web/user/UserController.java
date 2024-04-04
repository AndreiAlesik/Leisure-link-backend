package work.web.user;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import work.dto.ResponseObject;
import work.dto.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import work.dto.user.userdetails.GetUserDetailsDTO;
import work.dto.user.userdetails.UpdateUserDetailsDTO;

import jakarta.servlet.http.HttpServletRequest;


public interface UserController {

    @PostMapping("/signup")
    @Operation(summary = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Email sent for verification"),
            @ApiResponse(responseCode = "202", description = "Verification code was sent once again"),
            @ApiResponse(responseCode = "400", description = "User already added")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseObject userRegisterAccount(@RequestBody @Valid RequestUserDTO requestUserDto);


    @PostMapping("/signin")
    @Operation(summary = "Endpoint to login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Bad Request: User was not registered"),
            @ApiResponse(responseCode = "401", description = "Wrong data supplied"),
            @ApiResponse(responseCode = "422", description = "Verification code was sent once again"),
    })
    ResponseObject login(@RequestBody RequestLoginDTO userLoginDto);

    @GetMapping("/user-details")
    @Operation(summary = "Get user details", description = "Get user details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "USER_NOT_FOUND"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    GetUserDetailsDTO getUserDetails(HttpServletRequest request);

    @PutMapping("/user-details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseObject updateUserDetails(HttpServletRequest request, @RequestBody UpdateUserDetailsDTO detailsDTO);

    @PatchMapping("/user-details/photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseObject updateUserImage(HttpServletRequest request, @ModelAttribute MultipartFile photo);

    @GetMapping("/user-id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    GetUserIdDTO getMyId(HttpServletRequest request);
}
