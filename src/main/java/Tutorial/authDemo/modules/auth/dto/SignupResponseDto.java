package Tutorial.authDemo.modules.auth.dto;

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
@Builder
public class SignupResponseDto implements Serializable {

  private String accessToken;
  private String refreshToken;
}
