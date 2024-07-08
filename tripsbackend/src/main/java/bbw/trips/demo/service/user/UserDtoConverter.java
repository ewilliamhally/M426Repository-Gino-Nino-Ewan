package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.UserEntity;

/** Convert UserEntity to UserDto. */

public class UserDtoConverter {
    /**
     * Convert UserEntity to UserDto.
     *
     * @param userEntity {@link bbw.trips.demo.repository.model.UserEntity}.
     * @return {@link bbw.trips.demo.model.UserDto}.
     */
    public static UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        return UserDto.builder().id(userEntity.getId()).firstname(userEntity.getFirstname()).lastname(userEntity.getLastname()).email(userEntity.getEmail()).build();
    }
}