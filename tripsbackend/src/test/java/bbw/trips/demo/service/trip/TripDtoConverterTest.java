package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.TripModelEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripDtoConverterTest {

    @Test
    void convertTripModelEntityToTripModelDto() {
        // Given
        TripModelEntity tripModelEntity = TripModelEntity.builder()
                .id(1L)
                .location("Location")
                .image("Image")
                .name("Trip Name")
                .description("Description")
                .userId(1L)
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstname("User Name")
                .email("user@example.com")
                .build();

        // When
        TripModelDto tripModelDto = TripDtoConverter.convertTripModelEntityToTripModelDto(tripModelEntity, userDto);

        // Then
        assertEquals(tripModelEntity.getId(), tripModelDto.getId());
        assertEquals(tripModelEntity.getLocation(), tripModelDto.getLocation());
        assertEquals(tripModelEntity.getImage(), tripModelDto.getImage());
        assertEquals(tripModelEntity.getName(), tripModelDto.getName());
        assertEquals(tripModelEntity.getDescription(), tripModelDto.getDescription());
        assertEquals(userDto, tripModelDto.getUserDto());
    }
}
