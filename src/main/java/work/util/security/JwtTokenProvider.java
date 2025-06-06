package work.util.security;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import work.domain.AppUserRole;
import work.util.exception.AuthenticationException;
import work.util.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservice's environment, this key would be kept on a config-server.
   */
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  private SecretKey key;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h

  private MyUserDetails myUserDetails;

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  public JwtTokenProvider(String secretKey, long validityInMilliseconds) {
    this.secretKey = secretKey;
    this.validityInMilliseconds = validityInMilliseconds;
  }

  public JwtTokenProvider(
      String secretKey, long validityInMilliseconds, @Lazy MyUserDetails myUserDetails) {
    this.secretKey = secretKey;
    this.validityInMilliseconds = validityInMilliseconds;
    this.myUserDetails = myUserDetails;
  }

  public JwtTokenProvider(@Lazy MyUserDetails myUserDetails) {
    this.myUserDetails = myUserDetails;
  }

  public JwtTokenProvider() {}

  @PostConstruct
  protected void init() {
    // Use Keys.secretKeyFor to create a key that's guaranteed to be secure enough
    this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String createToken(String email, List<AppUserRole> appUserRoles) {
    Claims claims = Jwts.claims().setSubject(email);
    claims.put(
        "auth",
        appUserRoles.stream()
            .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    if (req.getHeader("Authorization") == null) {
      throw new AuthenticationException();
    }
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
