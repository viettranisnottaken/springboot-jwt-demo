package Tutorial.authDemo.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors
@AllArgsConstructor
public class SignupRequestDto {

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Password confirmation is required")
  private String passwordConfirmation;

  private String firstName;

  private String lastName;
}
