package bbw.trips.demo.service.security;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {
    private final UserService userService;

    public UserDto getUserDtoFromSecurityHolder() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findUserWithMail(userDetails.getUsername());
    }
}