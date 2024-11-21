package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.modules.user.dto.UserResponseDto;
import Tutorial.authDemo.shared.Constants.ERROR;
import Tutorial.authDemo.shared.exception.BadRequestException;
import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Autowired
  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping("")
  public ResponseEntity<BaseResponse<UserResponseDto>> createUser(
      @RequestBody UserRequestDto payload) {
    // 400
    if (payload == null || payload.getEmail() == null || payload.getPassword() == null) {
      throw new BadRequestException(ERROR.REQUEST.INVALID_BODY);
    }

    // 409 thrown in service, handled in global exception
    // return user
    // get user from service -> map to dto -> respond
    UserEntity createdUser = this.userService.create(payload);
    UserResponseDto responseDto = this.userMapper.entityToResponseDto(createdUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        BaseResponse.<UserResponseDto>builder().status(ResponseStatus.SUCCESS)
            .message(HttpStatus.OK.toString()).data(responseDto).build());
  }

  @GetMapping("")
  public ResponseEntity<BaseResponse<List<UserResponseDto>>> getAllUsers(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "DESC") String sortDirection,
      @RequestParam(required = false) String name) {

    Page<UserResponseDto> users = this.userService.getUsers(page, size, sortBy, sortDirection, name)
        .map(
            this.userMapper::entityToResponseDto
        );

    BaseResponse<List<UserResponseDto>> response =
        BaseResponse.<List<UserResponseDto>>builder()
            .message(HttpStatus.OK.toString())
            .totalPages(users.getTotalPages())
            .pageIndex(page)
            .pageSize(size)
            .total(users.getTotalElements())
            .data(users.getContent())
            .status(ResponseStatus.SUCCESS)
            .build();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<UserResponseDto>> getUser(@PathVariable Long id) {
    // 400
    if (id == null) {
      throw new BadRequestException(ERROR.REQUEST.INVALID_BODY);
    }

    // 404 handled by global handler
    UserEntity user = this.userService.findById(id);

    // return user
    UserResponseDto userResponseDto = this.userMapper.entityToResponseDto(user);

    return ResponseEntity.ok(BaseResponse.<UserResponseDto>builder().status(ResponseStatus.SUCCESS)
        .message(HttpStatus.OK.toString()).data(userResponseDto).build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<BaseResponse<UserResponseDto>> updateUser(
      @RequestBody UserRequestDto payload, @PathVariable Long id) {
    // 400
    if (payload == null) {
      throw new BadRequestException(ERROR.REQUEST.INVALID_BODY);
    }

    // return user
    UserEntity updatedUser = this.userService.update(id, payload);
    UserResponseDto responseDto = this.userMapper.entityToResponseDto(updatedUser);

    return ResponseEntity.ok(BaseResponse.<UserResponseDto>builder().status(ResponseStatus.SUCCESS)
        .message(HttpStatus.OK.toString()).data(responseDto).build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<String>> deleteUser(@PathVariable Long id) {
    // 400
    if (id == null) {
      throw new BadRequestException(ERROR.REQUEST.INVALID_BODY);
    }

    // 404
    // return user
    this.userService.delete(id);

    return ResponseEntity.ok(BaseResponse.<String>builder().status(ResponseStatus.SUCCESS)
        .message(HttpStatus.OK.toString()).build());
  }
}
