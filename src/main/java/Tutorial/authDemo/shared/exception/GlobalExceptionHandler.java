package Tutorial.authDemo.shared.exception;

import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    BaseResponse<Object> response = BaseResponse.builder().errorMap(List.of(errors))
        .status(ResponseStatus.FAILED).build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BaseResponse<Object>> handleNotFoundExceptions(NotFoundException ex) {

    BaseResponse<Object> response = BaseResponse.builder().errors(List.of(ex.getMessage()))
        .status(ResponseStatus.FAILED).build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BaseResponse<Object>> handleBadRequestExceptions(BadRequestException ex) {

    BaseResponse<Object> response = BaseResponse.builder().errors(List.of(ex.getMessage()))
        .status(ResponseStatus.FAILED).build();

    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<BaseResponse<Object>> handleConflictExceptions(ConflictException ex) {

    BaseResponse<Object> response = BaseResponse.builder().errors(List.of(ex.getMessage()))
        .status(ResponseStatus.FAILED).build();

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<BaseResponse<Object>> handleUnauthorizedExceptions(
      UnauthorizedException ex) {

    BaseResponse<Object> response = BaseResponse.builder().errors(List.of(ex.getMessage()))
        .status(ResponseStatus.FAILED).build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }
}
