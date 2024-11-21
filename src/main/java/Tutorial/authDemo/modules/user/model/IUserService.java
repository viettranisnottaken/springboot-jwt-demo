package Tutorial.authDemo.modules.user.model;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import org.springframework.data.domain.Page;

public interface IUserService {

  UserEntity create(UserRequestDto userRequestDto);

  Page<UserEntity> getUsers(int pageNo, int pageSize, String sortBy, String sortDirection,
      String keyword);

  UserEntity findById(Long id);

  UserEntity findByEmail(String email);

  UserEntity update(Long id, UserRequestDto userRequestDto);

  void delete(Long id);
}
