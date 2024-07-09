package bbw.trips.demo.repository;

import bbw.trips.demo.repository.model.TripModelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class TripRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolderFactory keyHolderFactory;

    public List<TripModelEntity> getAllTrips() {
        String sql = "SELECT * FROM trips";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TripModelEntity tripModelEntity = TripModelEntity.builder().build();
            tripModelEntity.setId(rs.getLong("id"));
            tripModelEntity.setLocation(rs.getString("location"));
            tripModelEntity.setName(rs.getString("name"));
            tripModelEntity.setImage(rs.getString("image"));
            tripModelEntity.setDescription(rs.getString("description"));
            tripModelEntity.setUserId(rs.getLong("user_id"));
            return tripModelEntity;
        });
    }

    public TripModelEntity createTrips(TripModelEntity tripModel) {
        KeyHolder keyHolder = keyHolderFactory.keyHolder();
        String sql ="INSERT INTO trips (location, image, name, description, user_id) VALUES(?, ?, ?, ?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, tripModel.getLocation());
                    pst.setString(2, tripModel.getImage());
                    pst.setString(3, tripModel.getName());
                    pst.setString(4, tripModel.getDescription());
                    pst.setLong(5, tripModel.getUserId());
                    return pst;
                },
                keyHolder);
        BigInteger tripModelId = (BigInteger) keyHolder.getKeys().get("insert_id");
        tripModel.setId(Long.valueOf(String.valueOf(tripModelId)));
        return tripModel;
    }

    public void saveTrip(Long tripId, Long userId) {
        String sql ="INSERT INTO user_trips (user_id, trip_id) VALUES(?, ?)";
        jdbcTemplate.update(
                connection ->{
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setLong(1, userId);
                    pst.setLong(2, tripId);
                    return pst;
                });
    }

    public List<TripModelEntity> getAllSavedTrips(Long userId) {
        String sql = "SELECT trips.id, trips.location, trips.image, trips.name, trips.description, trips.user_id " +
                "FROM trips " +
                "JOIN user_trips ON trips.id = user_trips.trip_id " +
                "JOIN User ON User.id = user_trips.user_id " +
                "WHERE User.id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{userId},
                (rs, rowNum) -> mapRowToTripModelEntity(rs)
        );
    }

    public void removeSavedTrip(Long tripId, Long userId) {
        String sql = "DELETE FROM user_trips WHERE trip_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, tripId, userId);
    }

    private TripModelEntity mapRowToTripModelEntity(ResultSet rs) throws SQLException {
        return TripModelEntity.builder()
                .id(rs.getLong("id"))
                .location(rs.getString("location"))
                .image(rs.getBytes("image") != null ? new String(rs.getBytes("image")) : null)
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .userId(rs.getLong("user_id"))
                .build();
    }
}

