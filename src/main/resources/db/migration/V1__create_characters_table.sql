CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    alias VARCHAR(100),
    birth_date VARCHAR(20),
    height_cm INT,
    power_level INT,
    special_attacks VARCHAR(255),
    description TEXT,
    image VARCHAR(255)
);