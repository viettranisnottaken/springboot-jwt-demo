package Tutorial.authDemo.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors
@AllArgsConstructor
public class SignupRequestDto {

  private String email;
  private String password;
  private String passwordConfirmation;
}
