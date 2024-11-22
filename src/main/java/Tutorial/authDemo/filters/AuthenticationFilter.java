package Tutorial.authDemo.filters;

import Tutorial.authDemo.modules.auth.JwtUserDetailsService;
import Tutorial.authDemo.shared.Constants.ERROR.USER;
import Tutorial.authDemo.shared.exception.UnauthorizedException;
import Tutorial.authDemo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

  private final AntPathMatcher antPathMatcher = new AntPathMatcher();
  private final JwtUtil jwtUtil;
  private final JwtUserDetailsService jwtUserDetailsService;

  @Value("#{'${jwtDemo.app.auth.allowedPaths}'.split(',')}")
  private List<String> allowedPaths;

  @Autowired
  public AuthenticationFilter(JwtUtil jwtUtil, JwtUserDetailsService jwtUserDetailsService) {
    this.jwtUtil = jwtUtil;
    this.jwtUserDetailsService = jwtUserDetailsService;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return this.allowedPaths.stream()
        .anyMatch(allowedPath -> antPathMatcher.match(allowedPath, request.getServletPath()));
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // get token from header
    String token = this.getTokenFromHeader(request);

    if (token == null) {
      throw new UnauthorizedException(USER.WRONG_CREDENTIAL);
    }

    String email = this.jwtUtil.extractEmail(token);

    if (email == null) {
      throw new UnauthorizedException(USER.WRONG_CREDENTIAL);
    }

    UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);

    boolean isValid = this.jwtUtil.validateJwtToken(token, userDetails);

    if (!isValid) {
      throw new UnauthorizedException(USER.WRONG_CREDENTIAL);
    }

    // Set to SecurityContext
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    //    if (token != null) {
    //      boolean isValid = this.jwtUtil.validateJwtToken(token);
    //
    //      if (isValid) {
    //        String email = this.jwtUtil.extractEmail(token);
    //
    //        if (email != null) {
    //          UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(email);
    //
    //          // Set to SecurityContext
    //          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
    //              new UsernamePasswordAuthenticationToken(
    //                  userDetails, null, userDetails.getAuthorities());
    //          usernamePasswordAuthenticationToken.setDetails(
    //              new WebAuthenticationDetailsSource().buildDetails(request));
    //
    //
    // SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    //        }
    //      }
    //    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
      return null;
    }

    return bearerToken.substring(7);
  }
}
