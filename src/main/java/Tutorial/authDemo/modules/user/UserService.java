package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.modules.user.model.IUserService;
import Tutorial.authDemo.shared.Constants;
import Tutorial.authDemo.shared.exception.CommonException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public UserEntity create(UserRequestDto userRequestDto) {
    if (userRequestDto == null) {
      throw new CommonException(Constants.ERROR.REQUEST.INVALID_BODY);
    }

    UserEntity user = this.userRepository.findByEmail(userRequestDto.getEmail());

    if (user != null) {
      throw new CommonException(Constants.ERROR.USER.EXISTED);
    }

    // request -> entity
    // save
    return Optional.of(userRequestDto).map(this.userMapper::requestDtoToEntity)
        .map(this.userRepository::save)
        .orElseThrow(() -> new CommonException(Constants.ERROR.USER.CREATE));
  }

  @Override
  public UserEntity findByEmail(String email) {
    return Optional.ofNullable(this.userRepository.findByEmail(email))
        .orElseThrow(() -> new CommonException(Constants.ERROR.USER.NOT_FOUND));
  }

  @Override
  public UserEntity update(UserRequestDto userRequestDto) {
    return null;
  }

  @Override
  public void delete(String email) {

  }
}
