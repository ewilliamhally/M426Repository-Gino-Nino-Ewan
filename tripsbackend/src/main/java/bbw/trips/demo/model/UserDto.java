package bbw.trips.demo.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * UserDto model with attribute id, name
 */
@Data
@Builder
public class UserDto {
    private Long id;

    @Size(min = 2, message = "Write your firstname! ")
    @Size(max = 30, message = "The firstname can't be longer than 30 characters! ")
    private String firstname;
    @Size(min = 2, message = "Write your name! ")
    @Size(max = 30, message = "The lastname can't be longer than 30 characters! ")
    private String lastname;

    @Size(min = 2, message = "Write your email! ")
    @Size(max = 60, message = "The email can't be longer than 60 characters! ")
    @Pattern(regexp = "^(.+)@(\\S+)\\.(\\S+)$")
    private String email;
}
