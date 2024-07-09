CREATE TABLE User (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      firstname VARCHAR(30) NOT NULL ,
                      lastname VARCHAR(30) NOT NULL,
                      email VARCHAR(60) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NUll,
                      role VARCHAR(255)
);
CREATE TABLE trips (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       location VARCHAR(255),
                       image BLOB,
                       name VARCHAR(255),
                       description VARCHAR(255),
                       user_id BIGINT NOT NULL ,
                       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE );

CREATE TABLE user_trips (
                            user_id BIGINT,
                            trip_id BIGINT,
                            PRIMARY KEY (user_id, trip_id),
                            FOREIGN KEY (user_id) REFERENCES User(id),
                            FOREIGN KEY (trip_id) REFERENCES trips(id)
);
