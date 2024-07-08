package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.TripRepository;
import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.TripModelEntity;
import lombok.AllArgsConstructor;
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

    public TripModelDto saveTrips(TripModelDto tripModelDto) {
        UserDto userDto = tripModelDto.getUserDto();
        TripModelEntity tripModelEntity = convertTripModelDtoToTripModelEntity(tripModelDto);
        return convertTripModelEntityToTripModelDto(tripRepository.saveTrips(tripModelEntity), userDto);
    }
}
