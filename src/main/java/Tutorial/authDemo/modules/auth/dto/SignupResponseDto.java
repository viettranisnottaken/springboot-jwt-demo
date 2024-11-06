package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class SignupResponseDto {
    @NonNull
    private String accessToken;

    @NonNull
    private String refreshToken;
}
