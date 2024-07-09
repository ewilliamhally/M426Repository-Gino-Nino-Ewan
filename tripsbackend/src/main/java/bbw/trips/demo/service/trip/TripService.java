package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.TripRepository;
import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.TripModelEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bbw.trips.demo.service.trip.TripDtoConverter.convertTripModelEntityToTripModelDto;
import static bbw.trips.demo.service.trip.TripEntityConverter.convertTripModelDtoToTripModelEntity;

@AllArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<TripModelDto> getAllTrips(List<UserDto> userDtoList) {
        List<TripModelEntity> tripModelEntities = tripRepository.getAllTrips();
        return tripModelEntities.stream()
                .map(
                        tripModelEntity ->
                                convertTripModelEntityToTripModelDto(
                                        tripModelEntity,
                                        userDtoList.stream()
                                                .filter(dto -> dto.getId().equals(tripModelEntity.getUserId()))
                                                .findFirst()
                                                .orElse(UserDto.builder().build())))
                .collect(Collectors.toList());
    }

    public TripModelDto createTrips(TripModelDto tripModelDto) {
        UserDto userDto = tripModelDto.getUserDto();
        TripModelEntity tripModelEntity = convertTripModelDtoToTripModelEntity(tripModelDto);
        return convertTripModelEntityToTripModelDto(tripRepository.createTrips(tripModelEntity), userDto);
    }

    public ResponseEntity<Void> saveTrip(Long tripId, UserDto userDto) {
        tripRepository.saveTrip(tripId, userDto.getId());
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> removeSavedTrips(Long tripId, UserDto userDto) {
        tripRepository.removeSavedTrip(tripId, userDto.getId());
        return ResponseEntity.noContent().build();
    }


    public List<TripModelDto> getAllSavedTrips(List<UserDto> userDtoList, UserDto userDto) {
        List<TripModelEntity> tripModelEntities = tripRepository.getAllSavedTrips(userDto.getId());
        return tripModelEntities.stream()
                .map(
                        tripModelEntity ->
                                convertTripModelEntityToTripModelDto(
                                        tripModelEntity,
                                        userDtoList.stream()
                                                .filter(dto -> dto.getId().equals(tripModelEntity.getUserId()))
                                                .findFirst()
                                                .orElse(UserDto.builder().build())))
                .collect(Collectors.toList());
    }
}


