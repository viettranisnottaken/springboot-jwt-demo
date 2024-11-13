package Tutorial.authDemo.modules.user.model;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;

public interface IUserService {

  public UserEntity create(UserRequestDto userRequestDto);

  public UserEntity findByEmail(String email);

  public UserEntity update(UserRequestDto userRequestDto);

  public void delete(String email);
}
