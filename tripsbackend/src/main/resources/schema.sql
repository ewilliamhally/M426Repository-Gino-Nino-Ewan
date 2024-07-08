CREATE TABLE trips (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       location VARCHAR(255),
                       image BLOB,
                       name VARCHAR(255),
                       description VARCHAR(255)
);
CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      firstname VARCHAR(30) NOT NULL ,
                      lastname VARCHAR(30) NOT NULL,
                      email VARCHAR(60) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NUll,
                      role VARCHAR(255)
                  );
