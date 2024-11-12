package Tutorial.authDemo.modules.user.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@RequiredArgsConstructor
@Accessors
public class UserResponseDto {

  @NonNull
  private Long id;

  @NonNull
  private String email;
}
