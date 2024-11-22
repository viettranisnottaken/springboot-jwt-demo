package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.modules.user.dto.UserRequestDto;
import Tutorial.authDemo.modules.user.model.IUserService;
import Tutorial.authDemo.shared.Constants;
import Tutorial.authDemo.shared.exception.BadRequestException;
import Tutorial.authDemo.shared.exception.CommonException;
import Tutorial.authDemo.shared.exception.ConflictException;
import Tutorial.authDemo.shared.exception.NotFoundException;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
      throw new BadRequestException(Constants.ERROR.REQUEST.INVALID_BODY);
    }

    UserEntity user = this.userRepository.findByEmail(userRequestDto.getEmail());

    if (user != null) {
      throw new ConflictException(Constants.ERROR.USER.EXISTED);
    }

    // request -> entity
    // save
    return Optional.of(userRequestDto)
        .map(this.userMapper::requestDtoToEntity)
        .map(this.userRepository::save)
        .orElseThrow(() -> new CommonException(Constants.ERROR.USER.CREATE));
  }

  @Override
  public UserEntity findById(@Nonnull Long id) {
    return this.userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(Constants.ERROR.USER.NOT_FOUND));
  }

  @Override
  public UserEntity findByEmail(@Nonnull String email) {
    return Optional.ofNullable(this.userRepository.findByEmail(email))
        .orElseThrow(() -> new NotFoundException(Constants.ERROR.USER.NOT_FOUND));
  }

  @Override
  public Page<UserEntity> getUsers(
      int pageNo, int pageSize, String sortBy, String sortDirection, String name) {

    if (!sortDirection.equals(Sort.Direction.ASC.toString())
        && !sortDirection.equals(Sort.Direction.DESC.toString())) {
      throw new BadRequestException(Constants.ERROR.REQUEST.INVALID_BODY);
    }

    Pageable pageable =
        PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

    return name == null
        ? this.userRepository.findAll(pageable)
        : this.userRepository.findByName(name, pageable);
  }

  @Override
  public UserEntity update(@Nonnull Long id, @Nonnull UserRequestDto userRequestDto) {
    UserEntity existingUser = this.findById(id);
    UserEntity userEntity = this.userMapper.requestDtoToEntity(userRequestDto);
    UserEntity dao = mergeExistingUserDataWithNewData(existingUser, userEntity);

    return Optional.of(this.userRepository.save(dao))
        .orElseThrow(() -> new CommonException(Constants.ERROR.USER.UPDATE));
  }

  @Override
  public void delete(@Nonnull Long id) {
    this.findById(id);
    this.userRepository.deleteById(id);
  }

  private UserEntity mergeExistingUserDataWithNewData(UserEntity existingData, UserEntity newData) {
    Long id = newData.getId() == null ? existingData.getId() : newData.getId();
    String email = newData.getEmail() == null ? existingData.getEmail() : newData.getEmail();
    String password =
        newData.getHashedPassword() == null
            ? existingData.getHashedPassword()
            : newData.getHashedPassword();
    String firstName =
        newData.getFirstName() == null ? existingData.getFirstName() : newData.getFirstName();
    String lastName =
        newData.getLastName() == null ? existingData.getLastName() : newData.getLastName();

    return existingData.toBuilder()
        .id(id)
        .email(email)
        .hashedPassword(password)
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }
}
