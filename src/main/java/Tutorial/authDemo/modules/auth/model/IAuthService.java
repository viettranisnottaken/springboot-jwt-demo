package Tutorial.authDemo.modules.auth.model;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;

public interface IAuthService {

  SignupResponseDto signup(SignupRequestDto payload);

  LoginResponseDto login(LoginRequestDto payload);

}
