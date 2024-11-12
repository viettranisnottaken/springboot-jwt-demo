package Tutorial.authDemo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderUtil {

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}