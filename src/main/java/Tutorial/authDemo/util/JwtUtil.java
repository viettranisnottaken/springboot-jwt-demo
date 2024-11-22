package Tutorial.authDemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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
    return this.generateJwtToken(subject, Integer.parseInt(this.accessTokenTTL));
  }

  public String generateRefreshToken(String subject) {
    return this.generateJwtToken(subject, Integer.parseInt(this.refreshTokenTTL));
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public boolean validateJwtToken(String authToken, UserDetails userDetails) {
    return !isTokenExpired(authToken) && userDetails.getUsername().equals(extractEmail(authToken));
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  private String generateJwtToken(String subject, int ttl) {
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + ttl))
        .signWith(this.key(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(this.key()).build().parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
