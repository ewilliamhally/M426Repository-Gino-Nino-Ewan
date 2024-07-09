package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.UserEntity;

/** Convert UserDto to UserEntity. */

public class UserEntityConverter {
    /**
     * Convert UserEntity to UserDto.
     *
     * @param userDto {@link bbw.trips.demo.model.UserDto}.
     * @return {@link bbw.trips.demo.repository.model.UserEntity}.
     */
    public static UserEntity convertUserDtoToUserEntity(UserDto userDto) {
        return UserEntity.builder().firstname(userDto.getFirstname()).lastname(userDto.getLastname()).email(userDto.getEmail()).build();
    }
}