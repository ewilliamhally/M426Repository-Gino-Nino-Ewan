package bbw.trips.demo.controller;

import bbw.trips.demo.security.auth.AuthenticationRequest;
import bbw.trips.demo.security.auth.AuthenticationResponse;
import bbw.trips.demo.security.auth.AuthenticationService;
import bbw.trips.demo.security.auth.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    /**
     * Controller method to handle POST requests at the endpoint "/register".
     * This method facilitates the registration of a user based on the provided registration request.
     *
     * @param  request The registration request, passed as a JSON object in the request body.
     *                The object is automatically deserialized from the HTTP request body.
     * @return ResponseEntity containing the result of the registration in the form of an AuthenticationResponse.
     *         The HTTP status code is set accordingly, with a successful call having a status code of 200 (OK).
     *         In case of an error, the status code is set accordingly, and error details are provided in the ResponseEntity body.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Controller method to handle POST requests at the endpoint "/authenticate".
     * This method facilitates user authentication based on the provided authentication request.
     *
     * @param request The authentication request, passed as a JSON object in the request body.
     *                The object is automatically deserialized from the HTTP request body.
     * @return ResponseEntity containing the result of the authentication in the form of an AuthenticationResponse.
     *         The HTTP status code is set accordingly, with a successful authentication having a status code of 200 (OK).
     *         In case of an error during authentication, the status code is set accordingly,
     *         and error details are provided in the ResponseEntity body.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
