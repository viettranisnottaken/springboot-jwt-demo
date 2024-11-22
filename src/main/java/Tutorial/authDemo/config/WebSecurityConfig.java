package Tutorial.authDemo.config;

import Tutorial.authDemo.filters.AuthenticationFilter;
import Tutorial.authDemo.modules.auth.JwtUserDetailsService;
import Tutorial.authDemo.shared.entryPoints.JwtAuthenticationEntryPoint;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final PasswordEncoder passwordEncoder;
  private AuthenticationFilter authenticationFilter;

  @Value("#{'${jwtDemo.app.auth.allowedPaths}'.split(',')}")
  private List<String> allowedPaths;

  @Autowired
  public WebSecurityConfig(
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      PasswordEncoder passwordEncoder,
      AuthenticationFilter authenticationFilter) {
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.passwordEncoder = passwordEncoder;
    this.authenticationFilter = authenticationFilter;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new JwtUserDetailsService();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(this.passwordEncoder);
    return authenticationProvider;
  }

  @Order(1)
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.addExposedHeader("Authorization");
    corsConfig.addAllowedOriginPattern("*"); // Allow all origins
    corsConfig.addAllowedMethod("*"); // Allow all HTTP methods
    corsConfig.addAllowedHeader("*"); // Allow all headers

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsFilter(source);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers(this.allowedPaths.toArray(new String[0]))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(
            httpSEHC -> httpSEHC.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
