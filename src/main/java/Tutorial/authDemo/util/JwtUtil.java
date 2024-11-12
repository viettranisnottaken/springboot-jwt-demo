package Tutorial.authDemo.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${jwtDemo.app.jwtSecret}")
  private String jwtSecret;

  @Value("${jwtDemo.app.accessTokenTTL}")
  private String accessTokenTTL;

  @Value("${jwtDemo.app.refreshTokenTTL}")
  private String refreshTokenTTL;

  public String generateAccessToken(String subject) {
    return this.generateJwtToken(subject, Short.parseShort(this.accessTokenTTL));
  }

  public String generateRefreshToken(String subject) {
    return this.generateJwtToken(subject, Integer.parseInt(this.refreshTokenTTL));
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  private String generateJwtToken(String subject, int ttl) {
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + ttl))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
}
