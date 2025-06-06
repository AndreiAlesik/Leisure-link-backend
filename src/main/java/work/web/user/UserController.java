package work.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import work.dto.ResponseObject;
import work.dto.user.*;
import work.dto.user.userdetails.GetUserDetailsDTO;
import work.dto.user.userdetails.UpdateUserDetailsDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserController {

  @PostMapping("/signup")
  @Operation(summary = "Create a new user account")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Email sent for verification"),
        @ApiResponse(responseCode = "202", description = "Verification code was sent once again"),
        @ApiResponse(responseCode = "400", description = "User already added")
      })
  @ResponseStatus(HttpStatus.ACCEPTED)
  ResponseObject userRegisterAccount(@RequestBody @Valid RequestUserDTO requestUserDto);

  @PostMapping("/signin")
  @Operation(summary = "Endpoint to login user")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Bad Request: User was not registered"),
        @ApiResponse(responseCode = "401", description = "Wrong data supplied"),
        @ApiResponse(responseCode = "422", description = "Verification code was sent once again"),
      })
  ResponseObject login(@RequestBody RequestLoginDTO userLoginDto);

  @PostMapping("/resetpassword/{email}    ")
  @Operation(summary = "Endpoint to reset user password")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "400", description = "Something went wrong"), //
        @ApiResponse(responseCode = "422", description = "Invalid username/password supplied")
      })
  ResponseObject resetPassword(@PathVariable("email") String email);

  @PatchMapping("/resetpassword/{code}")
  @Operation(summary = "Endpoint to check code from mail to reset password")
  @ApiResponses(
      value = { //
        @ApiResponse(responseCode = "401", description = "Invalid code provided"), //
        @ApiResponse(responseCode = "422", description = "Account is not activated"), //
        @ApiResponse(
            responseCode = "202",
            description = "Password reset code accepted, new code generated"), //
        @ApiResponse(responseCode = "400", description = "Provided code has expired")
      })
  ResponseObject checkCodeForPasswordResetting(@PathVariable("code") String code);

  @PostMapping("/confirmresetting")
  @Operation(summary = "Endpoint to confirm resetting tutor password")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "400", description = "Something went wrong"),
        @ApiResponse(responseCode = "422", description = "Invalid code supplied")
      })
  ResponseObject confirmPasswordResetting(@RequestBody PasswordResetDTO passwordResetDTO);

  @PutMapping("/reset-password")
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Reset password", description = "Reset user password.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "202", description = "PASSWORD_SUCCESSFULLY_UPDATED"),
      })
  ResponseObject resetPassword(HttpServletRequest request, @RequestBody ChangePasswordDTO password);

  @GetMapping("/user-details")
  @PreAuthorize("hasRole('ROLE_USER')")
  @Operation(summary = "Get user details", description = "Get user details.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "USER_NOT_FOUND"),
      })
  @SecurityRequirement(name = "Bearer Authentication")
  GetUserDetailsDTO getUserDetails(HttpServletRequest request);

  @PutMapping("/user-details")
  @PreAuthorize("hasRole('ROLE_USER')")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
      })
  @SecurityRequirement(name = "Bearer Authentication")
  ResponseObject updateUserDetails(
      HttpServletRequest request, @RequestBody UpdateUserDetailsDTO detailsDTO);

  @PatchMapping("/user-details/photo")
  @PreAuthorize("hasRole('ROLE_USER')")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
      })
  @SecurityRequirement(name = "Bearer Authentication")
  ResponseObject updateUserImage(HttpServletRequest request, @ModelAttribute MultipartFile photo);

  @GetMapping("/user-id")
  @PreAuthorize("hasRole('ROLE_USER')")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "DATA_SUCCESSFULLY_UPDATED"),
      })
  @SecurityRequirement(name = "Bearer Authentication")
  GetUserIdDTO getMyId(HttpServletRequest request);
}
