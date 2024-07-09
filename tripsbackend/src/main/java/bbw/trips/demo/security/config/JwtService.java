package bbw.trips.demo.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
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

    private static final String SECRET_KEY =
            "849f33fbccf2760c5525b043816ab4042ddf153ad64d903a450790413bd13863";

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * <p>This method uses the provided token and extracts the subject claim, which typically
     * represents the username of the authenticated user.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username (subject) extracted from the JWT token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the provided JWT token using a custom claims resolver function.
     *
     * <p>This method utilizes a custom claims resolver function to extract a specific claim from the
     * provided JWT token. It first extracts all claims using the {@code extractAllClaims} method and
     * then applies the custom resolver function to obtain the desired claim.
     *
     * @param <T> The type of the extracted claim.
     * @param token The JWT token from which to extract the claim.
     * @param claimsResolver The custom claims resolver function to extract a specific claim.
     * @return The extracted claim of type T from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the provided UserDetails.
     *
     * <p>This method creates a JWT token by invoking the overloaded {@code generateToken} method with
     * an empty claims map and the provided UserDetails.
     *
     * @param userDetails The UserDetails representing the authenticated user.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims for the provided UserDetails.
     *
     * <p>This method creates a JWT token by combining the extra claims, user details, and other
     * standard JWT attributes such as subject, issued-at, and expiration. It uses the signing key and
     * the HS256 algorithm for signing the token.
     *
     * @param extraClaims Additional claims to be included in the JWT token.
     * @param userDetails The UserDetails representing the authenticated user.
     * @return The generated JWT token with additional claims.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates whether a JWT token is valid for the provided UserDetails.
     *
     * <p>This method checks if the username extracted from the token matches the username of the
     * provided UserDetails and if the token is not expired.
     *
     * @param token The JWT token to be validated.
     * @param userDetails The UserDetails representing the authenticated user.
     * @return True if the token is valid for the provided UserDetails; false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks whether a JWT token has expired.
     *
     * <p>This method compares the expiration date extracted from the token with the current date to
     * determine if the token has expired.
     *
     * @param token The JWT token to be checked for expiration.
     * @return True if the token has expired; false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * <p>This method uses the provided token and extracts the expiration date claim, representing the
     * date and time when the token is set to expire.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the JWT token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the provided JWT token.
     *
     * <p>This method uses the provided token and extracts all claims, representing the set of
     * assertions made about the user.
     *
     * @param token The JWT token from which to extract all claims.
     * @return The set of claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT token verification.
     *
     * <p>This method decodes the base64-encoded secret key and converts it into a HMAC-SHA key, which
     * is used for verifying the authenticity of JWT tokens.
     *
     * @return The signing key used for JWT token verification.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}