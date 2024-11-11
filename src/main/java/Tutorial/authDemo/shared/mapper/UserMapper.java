package Tutorial.authDemo.shared.mapper;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IBaseMapper<UserResponseDto, UserEntity> {

  @Override
  public UserResponseDto entityToDto(UserEntity userEntity) {
    return new UserResponseDto(userEntity.getId(), userEntity.getEmail());
  }

  @Override
  public UserEntity dtoToEntity(UserResponseDto userResponseDto) {
    return UserEntity.builder().id(userResponseDto.getId()).email(userResponseDto.getEmail())
        .build();
  }
}
