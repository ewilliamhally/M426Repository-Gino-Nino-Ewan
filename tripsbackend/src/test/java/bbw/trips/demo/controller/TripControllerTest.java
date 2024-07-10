package bbw.trips.demo.controller;
import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.service.security.SecurityService;
import bbw.trips.demo.service.trip.TripService;
import bbw.trips.demo.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TripControllerTest {

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private TripController tripController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTrips() {
        // Given
        List<TripModelDto> mockTripList = Arrays.asList(
                TripModelDto.builder().id(1L).name("Trip 1").build(),
                TripModelDto.builder().id(2L).name("Trip 2").build()
        );
        when(userService.getAllUser()).thenReturn(Arrays.asList(UserDto.builder().id(1L).build()));
        when(tripService.getAllTrips(any())).thenReturn(mockTripList);

        // When
        ResponseEntity<List<TripModelDto>> responseEntity = tripController.getAllTrips();

        // Then
        verify(userService, times(1)).getAllUser();
        verify(tripService, times(1)).getAllTrips(any());
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().size() == 2; // Assuming two mock trips were returned
    }

    @Test
    public void testCreateNewTrip() {
        // Given
        TripModelDto tripModelDto = TripModelDto.builder().id(1L).name("New Trip").build();
        UserDto userDto = UserDto.builder().id(1L).build();
        when(securityService.getUserDtoFromSecurityHolder()).thenReturn(userDto);
        when(tripService.createTrips(any())).thenReturn(tripModelDto);

        // When
        ResponseEntity<TripModelDto> responseEntity = tripController.createNewTrip(tripModelDto);

        // Then
        verify(securityService, times(1)).getUserDtoFromSecurityHolder();
        verify(tripService, times(1)).createTrips(any());
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().getId() == 1L; // Assuming ID is set correctly
    }

    @Test
    public void testSaveTrip() {
        // Given
        String tripId = "1";
        UserDto userDto = UserDto.builder().id(1L).build();
        when(securityService.getUserDtoFromSecurityHolder()).thenReturn(userDto);
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        when(tripService.saveTrip(anyLong(), any())).thenReturn(expectedResponse);

        // When
        ResponseEntity<Void> responseEntity = tripController.saveTrip(tripId);

        // Then
        verify(securityService, times(1)).getUserDtoFromSecurityHolder();
        verify(tripService, times(1)).saveTrip(anyLong(), any());
        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    public void testRemoveSavedTrips() {
        // Given
        String tripId = "1";
        UserDto userDto = UserDto.builder().id(1L).build();
        when(securityService.getUserDtoFromSecurityHolder()).thenReturn(userDto);
        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        when(tripService.removeSavedTrips(anyLong(), any())).thenReturn(expectedResponse);

        // When
        ResponseEntity<Void> responseEntity = tripController.removeSavedTrips(tripId);

        // Then
        verify(securityService, times(1)).getUserDtoFromSecurityHolder();
        verify(tripService, times(1)).removeSavedTrips(anyLong(), any());
        assert responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
    }

    @Test
    public void testGetAllSavedTrips() {
        // Given
        List<TripModelDto> mockTripList = Arrays.asList(
                TripModelDto.builder().id(1L).name("Saved Trip 1").build(),
                TripModelDto.builder().id(2L).name("Saved Trip 2").build()
        );
        UserDto userDto = UserDto.builder().id(1L).build();
        when(userService.getAllUser()).thenReturn(Arrays.asList(userDto));
        when(securityService.getUserDtoFromSecurityHolder()).thenReturn(userDto);
        when(tripService.getAllSavedTrips(any(), any())).thenReturn(mockTripList);

        // When
        ResponseEntity<List<TripModelDto>> responseEntity = tripController.getAllSavedTrips();

        // Then
        verify(userService, times(1)).getAllUser();
        verify(securityService, times(1)).getUserDtoFromSecurityHolder();
        verify(tripService, times(1)).getAllSavedTrips(any(), any());
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().size() == 2;
    }
}
