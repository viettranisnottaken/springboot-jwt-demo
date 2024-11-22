package Tutorial.authDemo.shared.entryPoints;

import Tutorial.authDemo.shared.Constants.ERROR;
import Tutorial.authDemo.shared.model.BaseResponse;
import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    BaseResponse<Object> errorResponse =
        BaseResponse.builder()
            .errors(List.of(ERROR.USER.WRONG_CREDENTIAL))
            .status(ResponseStatus.FAILED)
            .build();

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader("Content-Type", "application/json");
    response.getWriter().write(convertObjectToJson(errorResponse));
  }

  public String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
