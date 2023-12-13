CREATE TABLE passanger
(
    id      SERIAL PRIMARY KEY,
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    contact_number VARCHAR(20),
    email          VARCHAR(100),
    password       varchar(255),
    isLogin        BOOLEAN default false,
    is_admin       BOOLEAN default false
);


CREATE TABLE trains (
                        id SERIAL PRIMARY KEY,
                        train_number VARCHAR(255)
);


CREATE TABLE stations (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255)
);

CREATE TABLE routes (
                        id SERIAL PRIMARY KEY,
                        start_station_id INTEGER REFERENCES stations(id),
                        end_station_id INTEGER REFERENCES stations(id)
);

CREATE TABLE requests (
                          id SERIAL PRIMARY KEY,
                          passenger_id INTEGER REFERENCES passanger(id),
                          route_id INTEGER REFERENCES routes(id),
                          trip_datetime TIMESTAMP
);

CREATE TABLE tickets (
                         id SERIAL PRIMARY KEY,
                         request_id INTEGER REFERENCES requests(id),
                         train_id INTEGER REFERENCES trains(id),
                         price DECIMAL
);