CREATE TABLE trips (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       location VARCHAR(255),
                       image BLOB,
                       name VARCHAR(255),
                       description VARCHAR(255)
);