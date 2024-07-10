package bbw.trips.demo.repository;


import bbw.trips.demo.repository.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/** Unit test for the {@link UserRepository} class. */
@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock private JdbcTemplate jdbcTemplate;

    @Mock private KeyHolderFactory keyHolderFactory;
    @InjectMocks private UserRepository userRepository;

    @Test
    void getAllUser() {
        // Given
        UserEntity userEntity = UserEntity.builder().id(1L).build();
        given(
                jdbcTemplate.query(
                        eq("SELECT * FROM User"), ArgumentMatchers.<RowMapper<UserEntity>>any()))
                .willReturn(List.of(userEntity));

        // When
        List<UserEntity> allUser = userRepository.getAllUser();

        // Then
        assertEquals(allUser.size(), 1);
    }

    @Test
    void shouldStoreUser() {
        // Given
        ArgumentCaptor<KeyHolder> keyHolderArgumentCaptor = ArgumentCaptor.forClass(KeyHolder.class);
        ArgumentCaptor<PreparedStatementCreator> preparedStatementCreatorArgumentCaptor =
                ArgumentCaptor.forClass(PreparedStatementCreator.class);
        UserEntity userEntity = UserEntity.builder().id(1L).build();

        given(keyHolderFactory.keyHolder())
                .willReturn(new GeneratedKeyHolder(List.of(Map.of("insert_id", new BigInteger(String.valueOf(1)), "name", "Test User"))));
        // When
        userRepository.storeUser(userEntity);

        // Then
        verify(jdbcTemplate)
                .update(
                        preparedStatementCreatorArgumentCaptor.capture(), keyHolderArgumentCaptor.capture());
        assertEquals(new BigInteger(String.valueOf(1)), (BigInteger) keyHolderArgumentCaptor.getValue().getKeys().get("insert_id"));
        assertEquals("Test User", keyHolderArgumentCaptor.getValue().getKeys().get("name"));
    }

    @Test
    void getUserById() {
        // Given
        long userId = 1L;
        String userName = "John Doe";

        given(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class)))
                .willReturn(UserEntity.builder().id(userId).firstname(userName).build());

        // When
        UserEntity userEntity = userRepository.getUserById(userId);

        // Then
        assertEquals(userId, userEntity.getId());
        assertEquals(userName, userEntity.getFirstname());
    }
}