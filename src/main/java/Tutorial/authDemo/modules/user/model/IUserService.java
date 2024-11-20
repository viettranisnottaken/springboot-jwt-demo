package Tutorial.authDemo.modules.user.model;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;

public interface IUserService {

  UserEntity create(UserRequestDto userRequestDto);

  UserEntity findById(Long id);

  UserEntity findByEmail(String email);

  UserEntity update(Long id, UserRequestDto userRequestDto);

  void delete(Long id);
}
