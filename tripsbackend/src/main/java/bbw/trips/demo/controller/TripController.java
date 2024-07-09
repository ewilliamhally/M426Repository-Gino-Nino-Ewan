package bbw.trips.demo.controller;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.service.security.SecurityService;
import bbw.trips.demo.service.trip.TripService;
import bbw.trips.demo.service.user.UserService;
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
    private final UserService userService;
    private final SecurityService securityService;
    @GetMapping("/allTrips")
    public ResponseEntity<List<TripModelDto>> getAllTrips() {
        List<UserDto> userDtoList = userService.getAllUser();
        return ResponseEntity.ok(tripService.getAllTrips(userDtoList));
    }

    @PostMapping("/createTrips")
    public ResponseEntity<TripModelDto> createNewTrip(@RequestBody TripModelDto tripModelDto){
        UserDto userDto = securityService.getUserDtoFromSecurityHolder();
        tripModelDto.setUserDto(userDto);
        return ResponseEntity.ok(tripService.createTrips(tripModelDto));
    }

    @PostMapping("/saveTrip")
    public ResponseEntity<TripModelDto> saveTrip(@RequestBody TripModelDto tripModelDto){
        UserDto userDto = securityService.getUserDtoFromSecurityHolder();
        tripService.saveTrip(tripModelDto, userDto);
        return null;
    }

}
