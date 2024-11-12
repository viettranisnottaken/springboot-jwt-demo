package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.UserRepository;
import Tutorial.authDemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Autowired
  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto payload,
      @RequestHeader("Accept") String headers) {
    // 404
    // 400

    if (payload == null || payload.getEmail() == null || payload.getPassword() == null
        || payload.getPasswordConfirmation() == null) {
      return ResponseEntity.badRequest().build();
    }

    UserEntity existingUser = this.userRepository.findByEmail(payload.getEmail());

    if (existingUser != null) {
      return ResponseEntity.status(409).build();
    }

    String hashedPassword = this.passwordEncoder.encode(payload.getPassword());

    this.userRepository.save(
        UserEntity.builder()
            .id(null)
            .email(payload.getEmail()).hashedPassword(hashedPassword)
            .build()
    );

    String accessToken = this.jwtUtil.generateAccessToken(payload.getEmail());
    String refreshToken = this.jwtUtil.generateRefreshToken(payload.getEmail());

    SignupResponseDto response = new SignupResponseDto(accessToken, refreshToken);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto payload) {
    // 400

    if (payload == null || payload.getEmail() == null || payload.getPassword() == null) {
      return ResponseEntity.badRequest().build();
    }

    UserEntity user = this.userRepository.findByEmail(payload.getEmail());

    if (user == null) {
      return ResponseEntity.status(404).build();
    }

    if (!this.passwordEncoder.matches(payload.getPassword(), user.getHashedPassword())) {
      return ResponseEntity.status(401).build();
    }

    String accessToken = this.jwtUtil.generateAccessToken(user.getEmail());
    String refreshToken = this.jwtUtil.generateRefreshToken(user.getEmail());

    return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
  }
}
