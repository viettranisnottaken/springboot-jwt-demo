package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SignupResponseDto {
    private String accessToken;
    private String refreshToken;

    public SignupResponseDto getResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

        return this;
    }
}
