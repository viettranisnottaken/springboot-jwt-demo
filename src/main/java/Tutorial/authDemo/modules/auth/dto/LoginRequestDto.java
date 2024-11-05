package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;

    public LoginRequestDto getPayload(String email, String password) {
        this.email = email;
        this.password = password;

        return this;
    }
}
