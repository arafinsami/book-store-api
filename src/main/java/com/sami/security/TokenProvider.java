package com.sami.security;

import static com.sami.constants.SecurityConstant.AUDIENCE_MOBILE;
import static com.sami.constants.SecurityConstant.AUDIENCE_TABLET;
import static com.sami.constants.SecurityConstant.CLAIM_KEY_AUDIENCE;
import static com.sami.constants.SecurityConstant.CLAIM_KEY_CREATED;
import static com.sami.constants.SecurityConstant.CLAIM_KEY_ROLE;
import static com.sami.constants.SecurityConstant.CLAIM_KEY_USERNAME;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${jwt.token.secret}")
	private String secret;

	@Value("${jwt.expire.sec}")
	private Long accessTokenExpiration;

	@Value("${jwt.refreshTokenExpire.sec}")
	private Long refreshTokenExpiration;

	public String getUsernameFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		String username = claims.getSubject();
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		Date created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		Date expiration = claims.getExpiration();
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		String audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (Objects.nonNull(lastPasswordReset) && created.before(lastPasswordReset));
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		//claims.put(CLAIM_KEY_ROLE, getUniqueAuthorities(userDetails));
		final Date createdDate = new Date();
		claims.put(CLAIM_KEY_CREATED, createdDate);
		return doGenerateToken(claims, accessTokenExpiration);
	}

	public String generateRefreshToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		final Date createdDate = new Date();
		claims.put(CLAIM_KEY_CREATED, createdDate);
		return doGenerateToken(claims, refreshTokenExpiration);
	}

	private String doGenerateToken(Map<String, Object> claims, long expiration) {
		Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
		Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
		System.out.println("doGenerateToken " + createdDate);
		System.out.println("doGenerateToken " + expirationDate);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		String refreshedToken = doGenerateToken(claims, accessTokenExpiration);
		return refreshedToken;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		AuthUser user = (AuthUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
	}

	private Collection<?> getUniqueAuthorities(UserDetails userDetails) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(userDetails.getAuthorities());
		return authorities;
	}
}
