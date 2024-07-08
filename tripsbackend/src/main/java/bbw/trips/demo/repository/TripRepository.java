package bbw.trips.demo.repository;

import bbw.trips.demo.model.TripModelDto;
import bbw.trips.demo.repository.model.TripModelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
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

    public TripModelEntity saveTrips(TripModelEntity tripModel) {
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
}
