package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors
@RequiredArgsConstructor
public class LoginRequestDto {

  private String email;
  private String password;
}
