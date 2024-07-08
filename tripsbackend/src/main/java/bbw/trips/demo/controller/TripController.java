package bbw.trips.demo.controller;

import bbw.trips.demo.model.TirpModel;
import bbw.trips.demo.service.trip.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/trips")
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt CORS-Anfragen nur von localhost:3000
@AllArgsConstructor
public class TripController {
    private final TripService tripService;
    @GetMapping("/allTrips")
    public ResponseEntity<List<TirpModel>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @PostMapping("/saveTrips")
    public ResponseEntity<TirpModel> saveNewTrip(@RequestBody TirpModel tripModel){
        return ResponseEntity.ok(tripService.saveTrips(tripModel));
    }

}
