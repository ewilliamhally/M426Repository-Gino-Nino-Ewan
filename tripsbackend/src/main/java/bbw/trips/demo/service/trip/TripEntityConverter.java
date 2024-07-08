package bbw.trips.demo.service.trip;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.repository.model.TripModelEntity;

public class TripEntityConverter {
    public static TripModelEntity convertTripModelDtoToTripModelEntity(TripModelDto tripModelDto){
        return TripModelEntity.builder().id(tripModelDto.getId()).location(tripModelDto.getLocation()).image(tripModelDto.getImage()).name(tripModelDto.getName()).description(tripModelDto.getDescription()).userId(tripModelDto.getUserDto().getId()).build();
    }
}
