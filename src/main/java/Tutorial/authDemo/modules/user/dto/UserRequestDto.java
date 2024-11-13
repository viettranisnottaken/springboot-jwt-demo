package Tutorial.authDemo.modules.user.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@RequiredArgsConstructor
@Accessors
@SuperBuilder(toBuilder = true)
@Jacksonized
public class UserRequestDto implements Serializable {

  @NonNull
  private String email;

  @NonNull
  private String password;
}
