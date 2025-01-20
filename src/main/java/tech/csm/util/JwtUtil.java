package tech.csm.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import tech.csm.model.UserModel;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

	private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

	private static long expirationMs = 86400000;

	// Create a signing key
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ", "JWT").and()
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expirationMs)).signWith(getSigningKey()).compact();
	}

	// Extract the username from the token
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		String subject = claims.getSubject(); // Get the `sub` field

		try {
			// Parse the `sub` field as JSON
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> userInfo = objectMapper.readValue(subject, new TypeReference<Map<String, String>>() {
			});
			return userInfo.get("email"); // Extract and return the email field
		} catch (Exception e) {
			throw new RuntimeException("Failed to extract email from token", e);
		}
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserModel user) {
		Map<String, Object> claims = new HashMap<>();
		ObjectMapper objMapper = new ObjectMapper();
		String stringifiedUser = "";
		try {
			stringifiedUser = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			System.err.println("json parsing exception/n/n " + e.getMessage());
		}
		return createToken(claims, stringifiedUser);
	}

	// Validate the token
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
