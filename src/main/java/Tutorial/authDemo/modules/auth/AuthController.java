package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.UserService;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.shared.Constants;
import Tutorial.authDemo.shared.Constants.ERROR.REQUEST;
import Tutorial.authDemo.shared.exception.CommonException;
import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import Tutorial.authDemo.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/signup")
  public ResponseEntity<BaseResponse<SignupResponseDto>> signup(
      @RequestBody SignupRequestDto payload) {
    if (payload == null || payload.getEmail() == null || payload.getPassword() == null
        || payload.getPasswordConfirmation() == null || !payload.getPassword()
        .equals(payload.getPasswordConfirmation())) {
      BaseResponse<SignupResponseDto> response = BaseResponse.<SignupResponseDto>builder()
          .errors(List.of(REQUEST.INVALID_BODY)).status(ResponseStatus.FAILED).build();

      return ResponseEntity.badRequest().body(response);
    }

    try {
      UserEntity user = this.userService.create(
          new UserRequestDto(payload.getEmail(), payload.getPassword()));

      String accessToken = this.jwtUtil.generateAccessToken(user.getEmail());
      String refreshToken = this.jwtUtil.generateRefreshToken(user.getEmail());

      BaseResponse<SignupResponseDto> response = BaseResponse.<SignupResponseDto>builder()
          .status(ResponseStatus.SUCCESS).message(HttpStatus.OK.toString()).data(
              SignupResponseDto.builder().accessToken(accessToken).refreshToken((refreshToken))
                  .build()).build();

      return ResponseEntity.ok(response);
    } catch (CommonException error) {
      BaseResponse<SignupResponseDto> response = BaseResponse.<SignupResponseDto>builder()
          .errors(List.of(error.getMessage())).status(ResponseStatus.FAILED).build();

      return ResponseEntity.status(409).body(response);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponseDto>> login(
      @RequestBody LoginRequestDto payload) {
    // 400

    if (payload == null || payload.getEmail() == null || payload.getPassword() == null) {
      BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
          .errors(List.of(REQUEST.INVALID_BODY)).status(ResponseStatus.FAILED).build();

      return ResponseEntity.badRequest().body(response);
    }

    // DAO here?
    try {
      UserEntity user = this.userService.findByEmail(payload.getEmail());

      if (user == null) {
        BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
            .errors(List.of(Constants.ERROR.USER.NOT_FOUND)).status(ResponseStatus.FAILED).build();

        return ResponseEntity.status(404).body(response);
      }

      if (!this.passwordEncoder.matches(payload.getPassword(), user.getHashedPassword())) {
        BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
            .errors(List.of(Constants.ERROR.USER.WRONG_CREDENTIAL)).status(ResponseStatus.FAILED)
            .build();

        return ResponseEntity.status(401).body(response);
      }

      String accessToken = this.jwtUtil.generateAccessToken(user.getEmail());
      String refreshToken = this.jwtUtil.generateRefreshToken(user.getEmail());

      BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
          .status(ResponseStatus.SUCCESS).message(HttpStatus.OK.toString()).data(
              LoginResponseDto.builder().accessToken(accessToken).refreshToken(refreshToken)
                  .build()).build();

      return ResponseEntity.ok().body(response);

    } catch (CommonException e) {
      BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
          .errors(List.of(e.getMessage())).status(ResponseStatus.FAILED).build();

      return ResponseEntity.status(409).body(response);
    }
  }
}
