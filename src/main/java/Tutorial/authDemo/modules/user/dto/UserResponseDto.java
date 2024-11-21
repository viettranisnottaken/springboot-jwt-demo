package Tutorial.authDemo.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@Accessors
@SuperBuilder(toBuilder = true)
@Jacksonized
public class UserResponseDto {

  @NonNull
  private Long id;

  @NonNull
  private String email;

  private String firstName;
  private String lastName;
}
