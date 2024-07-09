package bbw.trips.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TripModelDto {
    private Long id;
    private String location;
    private String image;
    private String name;
    private String description;
    private UserDto userDto;
}
