package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.auth.dto.LoginRequestDto;
import Tutorial.authDemo.modules.auth.dto.LoginResponseDto;
import Tutorial.authDemo.modules.auth.dto.SignupRequestDto;
import Tutorial.authDemo.modules.auth.dto.SignupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    LoginRequestDto loginRequest;
    LoginResponseDto loginResponse;
    SignupRequestDto signupRequest;
    SignupResponseDto signupResponse;

    @Autowired
    public AuthController(
        LoginRequestDto loginRequest,
        LoginResponseDto loginResponse,
        SignupRequestDto signupRequest,
        SignupResponseDto signupResponse
    ) {
        this.loginRequest = loginRequest;
        this.loginResponse = loginResponse;
        this.signupRequest = signupRequest;
        this.signupResponse = signupResponse;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto payload) {
        // 401
        // 404
        // 400

        return ResponseEntity.ok(
            signupResponse.getResponse(payload.getEmail(), payload.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto payload) {
        // 401
        // 404
        // 400

        return ResponseEntity.ok(
            loginResponse.getResponse(payload.getEmail(), payload.getPassword())
        );
    }
}
