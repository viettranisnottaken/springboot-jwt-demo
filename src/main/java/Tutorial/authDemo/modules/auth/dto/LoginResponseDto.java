package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto getResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        return this;
    }
}
