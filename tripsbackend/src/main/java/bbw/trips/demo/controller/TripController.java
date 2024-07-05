package bbw.trips.demo.controller;

import bbw.trips.demo.model.TirpModel;
import bbw.trips.demo.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
