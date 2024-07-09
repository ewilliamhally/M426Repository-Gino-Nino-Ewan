package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bbw.trips.demo.service.user.UserDtoConverter.convertUserEntityToUserDto;
import static bbw.trips.demo.service.user.UserEntityConverter.convertUserDtoToUserEntity;

/** User Service. */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Get all User from Database.
     *
     * @return List of {@link UserDto}.
     */
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntityList = userRepository.getAllUser();

        return userEntityList.stream()
                .map(UserDtoConverter::convertUserEntityToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * store new User
     * @param userDto
     * @return {@link UserDto}
     */
    public UserDto storeUser(UserDto userDto) {
        UserEntity userEntity = convertUserDtoToUserEntity(userDto);
        return convertUserEntityToUserDto(userRepository.storeUser(userEntity));
    }

    /**
     * Use the jwtAuthenticationFilter to get the mail from the jwt token and then send the request to the repository to find the user with this mail
     * @return {@link UserDto}
     */
    public UserEntity findUserWithMailNotConverted(String userEmail ){
        return userRepository.getUserByEmail(userEmail);
    }

    /**
     * Use the jwtAuthenticationFilter to get the mail from the jwt token and then send the request to the repository to find the user with this mail
     * @return {@link UserDto}
     */
    public UserDto findUserWithMail(String userEmail){
        return convertUserEntityToUserDto(userRepository.getUserByEmail(userEmail));
    }

    /**
     * get a User by Id
     * @param id
     * @return {@link Long}
     */
    public UserDto getUserById(Long id){
        UserEntity userEntity = userRepository.getUserById(id);
        return convertUserEntityToUserDto(userEntity);
    }
}