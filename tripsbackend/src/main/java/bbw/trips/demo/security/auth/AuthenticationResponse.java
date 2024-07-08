package bbw.trips.demo.security.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a response containing authentication information, typically a token.
 *
 * <p>The class is annotated with Lombok annotations for automatic generation of standard
 * methods such as getters, setters, toString, equals, hashCode, and a builder pattern.
 * The builder pattern allows for easy and flexible instantiation of objects.
 * </p>
 */
@Data
@Builder
public class AuthenticationResponse {

    private final String token;
}
