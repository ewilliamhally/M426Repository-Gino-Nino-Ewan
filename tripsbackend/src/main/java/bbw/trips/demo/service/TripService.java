package bbw.trips.demo.service;

import bbw.trips.demo.model.TirpModel;
import bbw.trips.demo.repository.TripRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;

    public List<TirpModel> getAllTrips() {
        return tripRepository.getAllTrips();
    }

    public TirpModel saveTrips(TirpModel tripModel) {
        return tripRepository.saveTrips(tripModel);
    }
}
