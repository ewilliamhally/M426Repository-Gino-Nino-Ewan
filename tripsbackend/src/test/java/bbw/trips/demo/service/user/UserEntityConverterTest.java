package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class UserEntityConverterTest {
    /** Unit test for the {@link UserEntityConverter} class. */
    @Test
    void convertUserDtoToUserEntity() {
        // Given
        UserDto userDto = UserDto.builder().id(2L).firstname("Test User").build();

        // When
        UserEntity userEntity = UserEntityConverter.convertUserDtoToUserEntity(userDto);

        // Then
        assertNull(userEntity.getId());
        assertEquals(userEntity.getFirstname(), userDto.getFirstname());
    }

    @Test
    void shouldReturnUserEntityWithTheInputNull() {
        // Given
        UserDto userDto = UserDto.builder().build();

        // When
        UserEntity userEntity = UserEntityConverter.convertUserDtoToUserEntity(userDto);

        // Then
        assertNull(userEntity.getId());
        assertNull(userEntity.getFirstname());
    }
}