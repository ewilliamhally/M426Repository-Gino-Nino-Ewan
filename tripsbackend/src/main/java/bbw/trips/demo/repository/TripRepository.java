package bbw.trips.demo.repository;

import bbw.trips.demo.model.TirpModel;
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

    public List<TirpModel> getAllTrips() {
        String sql = "SELECT * FROM trips";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TirpModel tirpModel = TirpModel.builder().build();
            tirpModel.setId(rs.getLong("id"));
            tirpModel.setLocation(rs.getString("location"));
            tirpModel.setName(rs.getString("name"));
            tirpModel.setImage(rs.getString("image"));
            tirpModel.setDescription(rs.getString("description"));
            return tirpModel;
        });
    }

    public TirpModel saveTrips(TirpModel tripModel) {
        KeyHolder keyHolder = keyHolderFactory.keyHolder();
        String sql ="INSERT INTO trips (location, image, name, description) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, tripModel.getLocation());
                    pst.setString(2, tripModel.getImage());
                    pst.setString(3, tripModel.getName());
                    pst.setString(4, tripModel.getDescription());
                    return pst;
                },
                keyHolder);
        BigInteger tripModelId = (BigInteger) keyHolder.getKeys().get("insert_id");
        tripModel.setId(Long.valueOf(String.valueOf(tripModelId)));
        return tripModel;
    }
}
