package bbw.trips.demo.security.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a request for user authentication.
 *
 * <p>The class is annotated with Lombok annotations for automatic generation of standard
 * methods such as getters, setters, toString, equals, hashCode, and a builder pattern.
 * The builder pattern allows for easy and flexible instantiation of objects.
 * </p>
 */
@Data
@Builder
public class AuthenticationRequest {

    private final String email;
    private final String password;
}