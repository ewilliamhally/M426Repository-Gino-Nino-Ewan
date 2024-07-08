package bbw.trips.demo.security.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


/**
 * Represents a request for user registration.
 *
 * <p>The class is annotated with Lombok annotations for automatic generation of standard
 * methods such as getters, setters, toString, equals, hashCode, and a builder pattern.
 * The builder pattern allows for easy and flexible instantiation of objects.
 * </p>
 */
@Data
@Builder
public class RegisterRequest {
    @Size(min = 2, message = "Write your firstname! ")
    @Size(max = 25, message = "The firstname can't be longer than 25 characters! ")
    private String firstname;
    @Size(min = 2, message = "Write your name! ")
    @Size(max = 40, message = "The lastname can't be longer than 40 characters! ")
    private String lastname;
    @Size(min = 2, message = "Write your email! ")
    @Size(max = 60, message = "The email can't be longer than 60 characters! ")
    @Pattern(regexp = "^(.+)@(\\S+)\\.(\\S+)$")
    private String email;
    @Size(min = 4, message = "Password too short!")
    @Size(max = 255, message = "The password can't be longer than 255 characters! ")
    private String password;
}