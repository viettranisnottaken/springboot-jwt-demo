package Tutorial.authDemo.modules.user.model;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;

public interface IUserService {

  UserEntity create(UserRequestDto userRequestDto);

  UserEntity findByEmail(String email);

  UserEntity update(UserRequestDto userRequestDto);

  void delete(String email);
}
