DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
--    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE url_checks (
--    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url_id BIGINT REFERENCES urls(id) ON DELETE CASCADE,
    status_code INT,
    h1 VARCHAR(255),
    title VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP NOT NULL
);