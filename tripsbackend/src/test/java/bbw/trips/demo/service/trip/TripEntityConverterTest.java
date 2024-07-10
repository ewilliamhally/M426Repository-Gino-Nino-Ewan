package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.TripModelEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripEntityConverterTest {

    @Test
    void convertTripModelDtoToTripModelEntity() {
        // Given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstname("User Name")
                .email("user@example.com")
                .build();

        TripModelDto tripModelDto = TripModelDto.builder()
                .id(1L)
                .location("Location")
                .image("Image")
                .name("Trip Name")
                .description("Description")
                .userDto(userDto)
                .build();

        // When
        TripModelEntity tripModelEntity = TripEntityConverter.convertTripModelDtoToTripModelEntity(tripModelDto);

        // Then
        assertEquals(tripModelDto.getId(), tripModelEntity.getId());
        assertEquals(tripModelDto.getLocation(), tripModelEntity.getLocation());
        assertEquals(tripModelDto.getImage(), tripModelEntity.getImage());
        assertEquals(tripModelDto.getName(), tripModelEntity.getName());
        assertEquals(tripModelDto.getDescription(), tripModelEntity.getDescription());
        assertEquals(tripModelDto.getUserDto().getId(), tripModelEntity.getUserId());
    }
}
