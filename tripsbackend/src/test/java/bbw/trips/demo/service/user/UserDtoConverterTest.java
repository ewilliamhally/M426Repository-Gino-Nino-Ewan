package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.model.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit test for the {@link UserDtoConverter} class.
 */
class UserDtoConverterTest {

    @Test
    void convertUserEntityToUserDto() {
        //Given
        UserEntity userEntity = UserEntity.builder().id(2L).firstname("Test User").lastname("Test User").email("Test User").password("Test User").build();

        //When
        UserDto userDto = UserDtoConverter.convertUserEntityToUserDto(userEntity);

        //Then
        assertEquals(userEntity.getId(), userDto.getId());
        assertEquals(userEntity.getFirstname(), userDto.getFirstname());
        assertEquals(userEntity.getLastname(), userDto.getLastname());
        assertEquals(userEntity.getEmail(),userDto.getEmail());
    }

    @Test
    void shouldReturnUserDtoWithTheInputNull() {
        // Given
        UserEntity userEntity = UserEntity.builder().build();

        // When
        UserDto userDto = UserDtoConverter.convertUserEntityToUserDto(userEntity);

        // Then
        assertEquals(userEntity.getId(), userDto.getId());
        assertEquals(userEntity.getFirstname(), userDto.getFirstname());
        assertEquals(userEntity.getLastname(), userDto.getLastname());
        assertEquals(userEntity.getEmail(),userDto.getEmail());
    }
}