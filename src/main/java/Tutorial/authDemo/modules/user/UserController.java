package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.modules.user.dto.UserResponseDto;
import Tutorial.authDemo.shared.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserController(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @RequestMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
    return ResponseEntity.ok(
        this.userMapper.entityToDto(this.userRepository.findById(Long.parseLong(id)).get()));
  }
}
