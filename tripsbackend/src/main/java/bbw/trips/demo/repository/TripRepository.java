package bbw.trips.demo.repository;

import bbw.trips.demo.model.TirpModel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class TripRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<TirpModel> getAllTrips() {
        String sql = "SELECT * FROM trips";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TirpModel tirpModel = TirpModel.builder().build();
            tirpModel.setId(rs.getLong("id"));
            tirpModel.setLocation(rs.getString("location"));
            tirpModel.setName(rs.getString("name"));
            tirpModel.setImage(rs.getString("image"));
            return tirpModel;
        });
    }
}
