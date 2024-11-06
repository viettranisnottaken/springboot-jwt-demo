package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.UserRepository;
import Tutorial.authDemo.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final CustomPasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(UserRepository userRepository, CustomPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto payload) {
    // 401
    // 404
    // 400

    String hashedPassword = this.passwordEncoder.encode(payload.password());

    this.userRepository.save(
        UserEntity.builder()
            .id(null)
            .email(payload.email()).hashedPassword(hashedPassword)
            .build()
    );

    return ResponseEntity.ok(new SignupResponseDto(payload.email(), payload.password()));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto payload) {
    // 401
    // 404
    // 400

    return ResponseEntity.ok(new LoginResponseDto(payload.email(), payload.password()));
  }
}
