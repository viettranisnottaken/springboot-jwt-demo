package Tutorial.authDemo.modules.auth.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Accessors
@AllArgsConstructor
@Jacksonized
@Builder(toBuilder = true)
public class LoginResponseDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 9067525132746788036L;
  private String accessToken;
  private String refreshToken;
}
