package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.modules.user.dto.UserResponseDto;
import Tutorial.authDemo.shared.model.IBaseMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IBaseMapper<UserEntity, UserRequestDto, UserResponseDto> {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserMapper(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserEntity requestDtoToEntity(UserRequestDto userRequestDto) {
    String hashedPassword = userRequestDto.getPassword() != null ? this.passwordEncoder.encode(
        userRequestDto.getPassword()) : null;

    return Optional.of(userRequestDto).map(
        e -> UserEntity.builder().id(null).email(e.getEmail()).firstName(e.getFirstName())
            .lastName(e.getLastName()).hashedPassword(hashedPassword).build()).orElse(null);
  }

  @Override
  public UserResponseDto entityToResponseDto(UserEntity userEntity) {
    return Optional.of(userEntity).map(
        e -> UserResponseDto.builder().id(e.getId()).email(e.getEmail()).firstName(e.getFirstName())
            .lastName(e.getLastName()).build()).orElse(null);
  }
}
