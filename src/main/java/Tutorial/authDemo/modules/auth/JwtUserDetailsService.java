package Tutorial.authDemo.modules.auth;

import Tutorial.authDemo.modules.user.UserEntity;
import Tutorial.authDemo.modules.user.UserService;
import io.micrometer.common.util.StringUtils;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity loggedInUser = userService.findByEmail(email);
    if (loggedInUser != null && StringUtils.isNotBlank(String.valueOf(loggedInUser.getId()))) {
      return new User(
          loggedInUser.getEmail(), loggedInUser.getHashedPassword(), Collections.emptyList());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + email);
    }
  }
}
