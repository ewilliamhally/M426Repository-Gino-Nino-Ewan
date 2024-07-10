package bbw.trips.demo.service.user;

import bbw.trips.demo.model.UserDto;
import bbw.trips.demo.repository.UserRepository;
import bbw.trips.demo.repository.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
/** Unit test for the {@link UserService} class. */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;

    @InjectMocks private UserService userService;
    private UserEntity userEntity;

    @BeforeEach
    void initialize() {
        userEntity = UserEntity.builder().id(1L).firstname("User1").build();
    }

    @Test
    void getAllUser() {
        // Given
        given(userRepository.getAllUser()).willReturn(List.of(userEntity));

        // When
        List<UserDto> allUser = userService.getAllUser();

        // Then
        assertEquals(allUser.size(), 1);
    }

    @Test
    void storeUser() {
        // Given
        UserEntity user1 = UserEntity.builder().firstname("User1").build();
        UserDto userDto = UserDto.builder().firstname("User1").build();
        given(userRepository.storeUser(user1)).willReturn(userEntity);

        // When
        UserDto storedUser = userService.storeUser(userDto);
        // Then
        assertEquals(storedUser.getId(), 1L);
        assertEquals(storedUser.getFirstname(), userEntity.getFirstname());
    }
    @Test
    void findUserWithMail(){
        //Given
        String userEmail="user12@test.com";
        UserEntity userEntity = UserEntity.builder().firstname("User12").lastname("User12").email(userEmail).build();
        UserDto userDto = UserDto.builder().firstname("User12").lastname("User12").email(userEmail).build();
        given(userRepository.getUserByEmail(userEmail)).willReturn(userEntity);
        //When
        UserDto userDtoTest= userService.findUserWithMail(userEmail);
        //Then
        assertEquals(userDtoTest.getEmail(),userEmail);
        assertEquals(userDtoTest,userDto);
    }
}