package bbw.trips.demo.repository;

import bbw.trips.demo.repository.model.TripModelEntity;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderFactory keyHolderFactory;

    @InjectMocks
    private TripRepository tripRepository;

    @Test
    void getAllTrips() {
        // Given
        TripModelEntity tripModelEntity = TripModelEntity.builder().id(1L).build();
        given(
                jdbcTemplate.query(
                        eq("SELECT * FROM trips"),
                        ArgumentMatchers.<RowMapper<TripModelEntity>>any())).willReturn(List.of(tripModelEntity));
        // When
        List<TripModelEntity> redditPostEntityList = tripRepository.getAllTrips();

        // Then
        assertEquals(redditPostEntityList.size(), 1);
        assertEquals(redditPostEntityList.get(0).getId(), 1L);

    }
    @Test
    void createTrips() {
        // Given
        ArgumentCaptor<KeyHolder> keyHolderArgumentCaptor = ArgumentCaptor.forClass(KeyHolder.class);
        ArgumentCaptor<PreparedStatementCreator> preparedStatementCreatorArgumentCaptor =
                ArgumentCaptor.forClass(PreparedStatementCreator.class);
        TripModelEntity tripModelEntity = TripModelEntity.builder().id(1L).build();
        given(keyHolderFactory.keyHolder())
                .willReturn(new GeneratedKeyHolder(List.of(Map.of("insert_id", new BigInteger(String.valueOf(1))))));

        // When
        tripRepository.createTrips(tripModelEntity);
        // Then
        verify(jdbcTemplate)
                .update(
                        preparedStatementCreatorArgumentCaptor.capture(), keyHolderArgumentCaptor.capture());
        assertEquals(new BigInteger(String.valueOf(1)), (BigInteger) keyHolderArgumentCaptor.getValue().getKeys().get("insert_id"));
    }
    @Test
    void getAllSavedTrips() throws SQLException {
        // Given
        Long userId = 1L;
        String sql = "SELECT trips.id, trips.location, trips.image, trips.name, trips.description, trips.user_id " +
                "FROM trips " +
                "JOIN user_trips ON trips.id = user_trips.trip_id " +
                "JOIN User ON User.id = user_trips.user_id " +
                "WHERE User.id = ?";

        // Mocking jdbcTemplate.query() method
        given(jdbcTemplate.query(
                eq(sql),
                ArgumentMatchers.<Object[]>any(),
                ArgumentMatchers.<RowMapper<TripModelEntity>>any()))
                .willReturn(List.of(
                        createMockTripModelEntity(1L, "Location1", "Image1", "Trip1", "Description1", 1L),
                        createMockTripModelEntity(2L, "Location2", "Image2", "Trip2", "Description2", 1L)
                ));

        // When
        List<TripModelEntity> trips = tripRepository.getAllSavedTrips(userId);

        // Then
        assertEquals(2, trips.size());

        // Verify individual fields of the returned TripModelEntity objects
        assertEquals(1L, trips.get(0).getId());
        assertEquals("Location1", trips.get(0).getLocation());
        assertEquals("Image1", trips.get(0).getImage());
        assertEquals("Trip1", trips.get(0).getName());
        assertEquals("Description1", trips.get(0).getDescription());
        assertEquals(1L, trips.get(0).getUserId());

        assertEquals(2L, trips.get(1).getId());
        assertEquals("Location2", trips.get(1).getLocation());
        assertEquals("Image2", trips.get(1).getImage());
        assertEquals("Trip2", trips.get(1).getName());
        assertEquals("Description2", trips.get(1).getDescription());
        assertEquals(1L, trips.get(1).getUserId());
    }

    // Helper method to create a mock TripModelEntity object
    private TripModelEntity createMockTripModelEntity(Long id, String location, String image, String name, String description, Long userId) {
        return TripModelEntity.builder()
                .id(id)
                .location(location)
                .image(image)
                .name(name)
                .description(description)
                .userId(userId)
                .build();
    }

}