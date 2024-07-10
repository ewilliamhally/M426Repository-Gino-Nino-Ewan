package bbw.trips.demo.controller;


import bbw.trips.demo.security.auth.AuthenticationRequest;
import bbw.trips.demo.security.auth.AuthenticationResponse;
import bbw.trips.demo.security.auth.AuthenticationService;
import bbw.trips.demo.security.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

/**
 * JUnit test class for {@link AuthenticationController}.
 *
 * <p>This class contains tests for the registration and authentication methods of the
 * AuthenticationController. It uses Mockito for creating mocks of dependencies such as
 * AuthenticationService to isolate the unit under test.
 *
 * <p>The tests cover various scenarios including successful registration and authentication.
 *
 * @author [Your Name]
 * @version 1.0
 * @see AuthenticationController
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Mock private AuthenticationService authenticationService;
    @InjectMocks AuthenticationController authenticationController;

    @Test
    void register() {

        // Given
        String token =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU";
        RegisterRequest request =
                RegisterRequest.builder()
                        .firstname("nino")
                        .lastname("siegrist")
                        .email("nino.siegrist@gmail.com")
                        .password("1234")
                        .build();
        given(authenticationService.register(request))
                .willReturn(
                        AuthenticationResponse.builder()
                                .token(
                                        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU")
                                .build());

        // When
        ResponseEntity<AuthenticationResponse> register = authenticationController.register(request);

        // Then
        assertNotNull(register.getBody());
        assertEquals(HttpStatus.OK, register.getStatusCode());
        assertEquals(token, register.getBody().getToken());
    }

    @Test
    void authenticate() {

        // Given
        String token =
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU";
        AuthenticationRequest authenticationRequest =
                AuthenticationRequest.builder().email("nino.siegrist@gmail.com").password("1234").build();
        given(authenticationService.authenticate(authenticationRequest))
                .willReturn(
                        AuthenticationResponse.builder()
                                .token(
                                        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YW5pcy5tZWljaHRyeUBuZXRjZXRlcmEuY29tIiwiaWF0IjoxNzAxODU3NjUyLCJleHAiOjE3MDE4NTkwOTJ9.EjuDkoHih0L3ZihA2y8ii-Z36NZrLIvpCsXQ_lfelfU")
                                .build());

        // When
        ResponseEntity<AuthenticationResponse> register =
                authenticationController.authenticate(authenticationRequest);

        // Then
        assertNotNull(register.getBody());
        assertEquals(HttpStatus.OK, register.getStatusCode());
        assertEquals(token, register.getBody().getToken());
    }
}
