package bbw.trips.demo.repository.model;

import bbw.trips.demo.model.UserDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class TripModelEntity {
    private Long id;
    private String location;
    private String image;
    private String name;
    private String description;
    private Long userId;
}
