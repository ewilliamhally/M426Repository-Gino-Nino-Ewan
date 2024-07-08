package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.TripModelEntity;

public class TripDtoConverter {

    public static TripModelDto convertTripModelEntityToTripModelDto(TripModelEntity tripModelEntity, UserDto userDto){
        return TripModelDto.builder().id(tripModelEntity.getId()).location(tripModelEntity.getLocation()).image(tripModelEntity.getImage()).name(tripModelEntity.getName()).description(tripModelEntity.getDescription()).userDto(userDto).build();
    }
}
