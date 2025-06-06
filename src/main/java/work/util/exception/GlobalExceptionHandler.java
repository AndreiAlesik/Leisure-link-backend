package work.util.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import work.dto.ResponseObject;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandler(Exception ex) {
    String errorMessage = ex.getMessage();
    if (ex.getCause() != null) {
      errorMessage = ex.getCause().getMessage();
    }
    ResponseObject errorDetails = new ResponseObject(HttpStatus.BAD_REQUEST, errorMessage, null);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<?> userAlreadyExistException(Exception exception) {
    var errorDetails = new ResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> userNotFoundException(Exception exception) {
    var errorDetails = new ResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();

    String message =
        fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

    ResponseObject errorDetails = new ResponseObject(HttpStatus.BAD_REQUEST, message, null);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handleAuthenticationException(Exception exception) {
    var errorDetails = new ResponseObject(HttpStatus.UNAUTHORIZED, null, null);
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<?> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpServletRequest request) {
    ResponseObject errorDetails =
        new ResponseObject(HttpStatus.NOT_FOUND, "ENDPOINT_NOT_FOUND", request.getRequestURI());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handleIllegalStateException(
      IllegalStateException exception, HttpServletRequest request) {
    ResponseObject errorDetails =
        new ResponseObject(HttpStatus.BAD_REQUEST, "BAD_FORMAT", request.getRequestURI());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
