package Tutorial.authDemo.modules.user.dto;

import java.io.Serial;
import java.io.Serializable;
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
public class UserRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = -6861349422255697210L;

  @NonNull
  private String email;

  private String password;
}
