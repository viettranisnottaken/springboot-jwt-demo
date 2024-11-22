package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signup")
  public ResponseEntity<BaseResponse<SignupResponseDto>> signup(
      @Valid @RequestBody SignupRequestDto payload) {
    SignupResponseDto responseDto = this.authService.signup(payload);

    BaseResponse<SignupResponseDto> response = BaseResponse.<SignupResponseDto>builder()
        .status(ResponseStatus.SUCCESS).message(HttpStatus.OK.toString())
        .data(responseDto).build();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponseDto>> login(
      @RequestBody LoginRequestDto payload) {
    LoginResponseDto loginResponseDto = this.authService.login(payload);

    BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
        .status(ResponseStatus.SUCCESS).message(HttpStatus.OK.toString()).data(loginResponseDto)
        .build();

    return ResponseEntity.ok().body(response);
  }
}
