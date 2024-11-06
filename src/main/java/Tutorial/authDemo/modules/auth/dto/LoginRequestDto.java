package Tutorial.authDemo.modules.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
