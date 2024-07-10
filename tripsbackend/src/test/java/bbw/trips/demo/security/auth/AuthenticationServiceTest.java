package bbw.trips.demo.security.auth;

import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.Role;
import bbw.trips.demo.repository.model.UserEntity;
import bbw.trips.demo.security.config.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * JUnit test class for {@link AuthenticationService}.
 *
 * <p>This class contains tests for the registration and authentication methods of the
 * AuthenticationService. It uses Mockito for creating mocks of dependencies such as UserRepository,
 * PasswordEncoder, JwtService, and AuthenticationManager to isolate the unit under test.
 *
 * <p>The tests cover various scenarios including successful registration and authentication.
 *
 * @author [Your Name]
 * @version 1.0
 * @see AuthenticationService
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock private UserRepository userRepository;

    @Mock private PasswordEncoder passwordEncoder;

    @Mock private JwtService jwtService;

    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private AuthenticationService authenticationService;

    @Test
    void register() {
        // Given
        String token =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU";
        RegisterRequest request =
                RegisterRequest.builder()
                        .firstname("John")
                        .lastname("Doe")
                        .email("john.doe@example.com")
                        .password("password123")
                        .build();

        UserEntity mockUserEntity =
                UserEntity.builder()
                        .id(1L)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password("hashedPassword") // assuming you have a hashed password here
                        .role(Role.USER)
                        .build();

        given(passwordEncoder.encode(request.getPassword())).willReturn("hashedPassword");
        given(userRepository.storeUser(any(UserEntity.class))).willReturn(mockUserEntity);
        given(jwtService.generateToken(mockUserEntity)).willReturn(token);

        // When
        AuthenticationResponse response = authenticationService.register(request);

        // Then
        assertEquals(token, response.getToken());
    }

    @Test
    void authenticate() {
        // Given
        String token =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU";
        AuthenticationRequest request =
                new AuthenticationRequest("john.doe@example.com", "password123");

        UserEntity mockUserEntity =
                UserEntity.builder()
                        .id(1L)
                        .firstname("John")
                        .lastname("Doe")
                        .email(request.getEmail())
                        .password("hashedPassword") // assuming you have a hashed password here
                        .role(Role.USER)
                        .build();

        given(userRepository.getUserByEmail(request.getEmail())).willReturn(mockUserEntity);
        given(jwtService.generateToken(mockUserEntity)).willReturn(token);

        // When
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Then
        assertEquals(token, response.getToken());
    }
}