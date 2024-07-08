package bbw.trips.demo.security.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * Custom authentication filter for processing JWT authentication.
 *
 * <p>This filter extracts the JWT from the "Authorization" header, validates it,
 * and sets up the Spring Security context if the token is valid. It then proceeds
 * to the next filter in the chain.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Constructs a new JwtAuthenticationFilter with the provided JWT service and user details service.
     */

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Getter
    private String jwt;
    @Getter
    private String authHeader;
    @Getter
    private String userEmail;

    /**
     * Processes authentication based on the provided JWT in the "Authorization" header.
     *
     * <p>Extracts the JWT, validates it, and sets up the Spring Security context if the token is valid.
     * Then, it proceeds to the next filter in the chain.
     * </p>
     *
     * @param request       The HTTP request.
     * @param response      The HTTP response.
     * @param filterChain   The filter chain.
     * @throws ServletException if an error occurs while processing the request.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt= authHeader.substring(7);
        userEmail= jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails) ) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}