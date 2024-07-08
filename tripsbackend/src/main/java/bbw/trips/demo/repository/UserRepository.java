package bbw.trips.demo.repository;

import bbw.trips.demo.repository.model.Role;
import bbw.trips.demo.repository.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/** User Repository Database. */
@Repository
@AllArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolderFactory keyHolderFactory;

    /**
     * getAllUser from Database ( mapping it to a UserEntity).
     *
     * @return List of {@link UserEntity}.
     */
    public List<UserEntity> getAllUser() {
        String sql = "SELECT * FROM \"User\"";
        return jdbcTemplate.query(
                sql,
                ((rs, rowNum) -> {
                    UserEntity userEntity =
                            UserEntity.builder()
                                    .id(rs.getLong("id"))
                                    .firstname(rs.getString("firstname"))
                                    .lastname(rs.getString("lastname"))
                                    .email(rs.getString("email"))
                                    .password(rs.getString("password"))
                                    .role(Role.valueOf(rs.getString("role")))
                                    .build();
                    return userEntity;
                }));
    }

    /**
     * store new User in Database
     *
     * @param userEntity
     * @return {@link UserEntity} with id from DB
     */
    public UserEntity storeUser(UserEntity userEntity) {
        String sql =
                "INSERT INTO User (firstname,lastname,email,password,role) VALUES(?,?,?,?,?)";
        KeyHolder keyHolder = keyHolderFactory.keyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pst.setString(1, userEntity.getFirstname());
                    pst.setString(2, userEntity.getLastname());
                    pst.setString(3, userEntity.getEmail());
                    pst.setString(4, userEntity.getPassword());
                    pst.setString(5, userEntity.getRole().getDbKey());
                    return pst;
                },
                keyHolder);
        BigInteger UserId = (BigInteger) keyHolder.getKeys().get("insert_id");
        userEntity.setId(Long.valueOf(String.valueOf(UserId)));
        return userEntity;
    }

    /**
     * Retrieves a user entity from the "reddit_user" table based on the specified user ID.
     *
     * @param userId The unique identifier of the user to be retrieved.
     * @return UserEntity object representing the user with the given ID.
     */
    public UserEntity getUserById(long userId) {
        String sql = "SELECT * FROM \"User\" WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {userId},
                (rs, rowNum) -> {
                    UserEntity userEntity =
                            UserEntity.builder()
                                    .id(rs.getLong("id"))
                                    .firstname(rs.getString("firstname"))
                                    .lastname(rs.getString("lastname"))
                                    .email(rs.getString("email"))
                                    .password(rs.getString("password"))
                                    .role(Role.valueOf(rs.getString("role")))
                                    .build();
                    return userEntity;
                });
    }

    /**
     * Retrieves a user entity from the "reddit_user" table based on the specified user email.
     *
     * @param userEmail The email address of the user to be retrieved.
     * @return UserEntity object representing the user with the given email.
     */
    public UserEntity getUserByEmail(String userEmail) {
        String sql = "SELECT * FROM User WHERE email = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[] {userEmail},
                (rs, rowNum) -> {
                    UserEntity userEntity =
                            UserEntity.builder()
                                    .id(rs.getLong("id"))
                                    .firstname(rs.getString("firstname"))
                                    .lastname(rs.getString("lastname"))
                                    .email(rs.getString("email"))
                                    .password(rs.getString("password"))
                                    .role(Role.valueOf(rs.getString("role")))
                                    .build();
                    return userEntity;
                });
    }
}
