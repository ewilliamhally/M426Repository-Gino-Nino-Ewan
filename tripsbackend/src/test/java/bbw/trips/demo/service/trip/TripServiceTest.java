package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.TripRepository;
import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.TripModelEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TripService tripService;

    @Test
    void getAllTrips() {
        // Given
        UserDto userDto = UserDto.builder().id(1L).build();
        TripModelEntity tripModelEntity = TripModelEntity.builder().id(1L).userId(1L).build();
        given(tripRepository.getAllTrips()).willReturn(List.of(tripModelEntity));

        // When
        List<TripModelDto> trips = tripService.getAllTrips(List.of(userDto));

        // Then
        assertEquals(1, trips.size());
        assertEquals(1L, trips.get(0).getId());
    }

    @Test
    void createTrips() {
        // Given
        UserDto userDto = UserDto.builder().id(1L).build();
        TripModelDto tripModelDto = TripModelDto.builder().id(1L).userDto(userDto).build();
        TripModelEntity tripModelEntity = TripModelEntity.builder().id(1L).userId(1L).build();
        given(tripRepository.createTrips(any(TripModelEntity.class))).willReturn(tripModelEntity);

        // When
        TripModelDto createdTrip = tripService.createTrips(tripModelDto);

        // Then
        assertEquals(1L, createdTrip.getId());
        verify(tripRepository).createTrips(any(TripModelEntity.class));
    }

    @Test
    void saveTrip() {
        // Given
        UserDto userDto = UserDto.builder().id(1L).build();
        Long tripId = 1L;

        // When
        ResponseEntity<Void> response = tripService.saveTrip(tripId, userDto);

        // Then
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(tripRepository).saveTrip(tripId, userDto.getId());
    }

    @Test
    void removeSavedTrips() {
        // Given
        UserDto userDto = UserDto.builder().id(1L).build();
        Long tripId = 1L;

        // When
        ResponseEntity<Void> response = tripService.removeSavedTrips(tripId, userDto);

        // Then
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(tripRepository).removeSavedTrip(tripId, userDto.getId());
    }

    @Test
    void getAllSavedTrips() {
        // Given
        UserDto userDto = UserDto.builder().id(1L).build();
        TripModelEntity tripModelEntity = TripModelEntity.builder().id(1L).userId(1L).build();
        given(tripRepository.getAllSavedTrips(userDto.getId())).willReturn(List.of(tripModelEntity));

        // When
        List<TripModelDto> trips = tripService.getAllSavedTrips(List.of(userDto), userDto);

        // Then
        assertEquals(1, trips.size());
        assertEquals(1L, trips.get(0).getId());
    }
}
