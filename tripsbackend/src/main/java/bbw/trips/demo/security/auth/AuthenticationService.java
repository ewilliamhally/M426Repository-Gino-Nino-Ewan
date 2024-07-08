package bbw.trips.demo.security.auth;

import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.Role;
import bbw.trips.demo.repository.model.UserEntity;
import bbw.trips.demo.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a user based on the provided registration request.
     *
     * <p>Processes the registration request, creates a new user entity, stores the user in the
     * repository, generates an authentication token for the registered user, and returns an
     * AuthenticationResponse containing the generated token.
     *
     * @param request The registration request containing user details.
     * @return AuthenticationResponse containing the authentication token for the registered user.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user =
                UserEntity.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();
        UserEntity userEntity = userRepository.storeUser(user);
        String generateToken = jwtService.generateToken(userEntity);
        return AuthenticationResponse.builder().token(generateToken).build();
    }

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * <p>Attempts to authenticate the user by verifying the provided email and password using the
     * authentication manager. If successful, generates an authentication token for the authenticated
     * user and returns an AuthenticationResponse containing the token.
     *
     * @param request The authentication request containing user credentials.
     * @return AuthenticationResponse containing the authentication token for the authenticated user.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity userEntity = userRepository.getUserByEmail(request.getEmail());
        String generateToken = jwtService.generateToken(userEntity);
        return AuthenticationResponse.builder().token(generateToken).build();
    }
}