package net.toujoustudios.kazunyaapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    public static final int TOKEN_LIFETIME = 1000 * 60 * 60 * 24;

    /**
     * Extracts the username from a given JWT token.
     *
     * @param token The token of the user.
     * @return The username saved inside the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract any claim from a given JWT token.
     *
     * @param token The token of the user.
     * @param claimsResolver A method to resolve the claim.
     * @return A value returned by the resolver.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token based on the given parameters. This token is used for login authentication.
     *
     * @param userDetails The information of the user to generate a token for.
     * @return The full JWT token as a string, without the "Bearer" prefix.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token based on the given parameters. This token is used for login authentication.
     *
     * @param extraClaims Any claims that the user has.
     * @param userDetails The information of the user to generate a token for.
     * @return The full JWT token as a string, without the "Bearer" prefix.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks whether a JWT token is valid, by comparing expiration date and claims.
     *
     * @param token The JWT token to verify.
     * @param userDetails The user details to compare with the token.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
