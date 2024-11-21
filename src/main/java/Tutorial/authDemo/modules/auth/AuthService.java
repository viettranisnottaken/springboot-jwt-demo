package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import Tutorial.authDemo.modules.auth.model.IAuthService;
import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.UserService;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.shared.Constants;
import Tutorial.authDemo.shared.Constants.ERROR.REQUEST;
import Tutorial.authDemo.shared.exception.BadRequestException;
import Tutorial.authDemo.shared.exception.UnauthorizedException;
import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import Tutorial.authDemo.util.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public SignupResponseDto signup(SignupRequestDto payload) {
    if (payload == null || payload.getEmail() == null || payload.getPassword() == null
        || payload.getPasswordConfirmation() == null || !payload.getPassword()
        .equals(payload.getPasswordConfirmation())) {
      throw new BadRequestException(REQUEST.INVALID_BODY);
    }

    UserEntity user = this.userService.create(
        new UserRequestDto(payload.getEmail(), payload.getPassword(), payload.getFirstName(),
            payload.getLastName()));

    String accessToken = this.jwtUtil.generateAccessToken(user.getEmail());
    String refreshToken = this.jwtUtil.generateRefreshToken(user.getEmail());

    return SignupResponseDto.builder().accessToken(accessToken).refreshToken((refreshToken))
        .build();
  }

  @Override
  public LoginResponseDto login(LoginRequestDto payload) {
    if (payload == null || payload.getEmail() == null || payload.getPassword() == null) {
      BaseResponse<LoginResponseDto> response = BaseResponse.<LoginResponseDto>builder()
          .errors(List.of(REQUEST.INVALID_BODY)).status(ResponseStatus.FAILED).build();

      throw new BadRequestException(REQUEST.INVALID_BODY);
    }

    UserEntity user = this.userService.findByEmail(payload.getEmail());

    if (!this.passwordEncoder.matches(payload.getPassword(), user.getHashedPassword())) {
      throw new UnauthorizedException(Constants.ERROR.USER.WRONG_CREDENTIAL);
    }

    String accessToken = this.jwtUtil.generateAccessToken(user.getEmail());
    String refreshToken = this.jwtUtil.generateRefreshToken(user.getEmail());

    return LoginResponseDto.builder().accessToken(accessToken).refreshToken((refreshToken))
        .build();
  }
}
