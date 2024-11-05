package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String passwordConfirmation;

    public SignupRequestDto getPayload(String email, String password, String passwordConfirmation) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;

        return this;
    }
}
