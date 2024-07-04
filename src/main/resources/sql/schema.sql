DROP TABLE Product IF EXISTS;
DROP TABLE Member IF EXISTS;

CREATE TABLE Product (
    id BIGINT,
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    imageUrl VARCHAR(255),
    primary key (id)
);

CREATE TABLE Member (
    id BIGINT AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);