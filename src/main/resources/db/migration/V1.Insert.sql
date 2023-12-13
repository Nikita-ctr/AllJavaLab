
INSERT INTO stations (name)
VALUES ('Station A'), ('Station B'), ('Station C'), ('Station D'), ('Station E');

INSERT INTO trains (train_number)
VALUES ('Train 1'), ('Train 2'), ('Train 3'), ('Train 4'), ('Train 5');

INSERT INTO passanger (first_name, last_name, contact_number, email, password, isLogin, is_admin)
VALUES
    ('John', 'Doe', '123456789', 'john.doe@example.com', 'password1', true, false),
    ('Jane', 'Smith', '987654321', 'jane.smith@example.com', 'password2', false, false),
    ('Michael', 'Johnson', '456789123', 'michael.johnson@example.com', 'password3', true, true),
    ('Emily', 'Davis', '789123456', 'emily.davis@example.com', 'password4', false, false),
    ('David', 'Wilson', '321654987', 'david.wilson@example.com', 'password5', true, false);

INSERT INTO routes (start_station_id, end_station_id)
VALUES
    (1, 3),
    (2, 4),
    (1, 5),
    (3, 2),
    (4, 1);

INSERT INTO requests (passenger_id, route_id, trip_datetime)
VALUES
    (1, 1, '2023-12-13 10:00:00'),
    (2, 3, '2023-12-14 14:30:00'),
    (3, 5, '2023-12-15 09:45:00'),
    (4, 2, '2023-12-16 16:15:00'),
    (5, 4, '2023-12-17 12:00:00');

INSERT INTO tickets (request_id, train_id, price)
VALUES
    (1, 1, 10.50),
    (2, 3, 15.75),
    (3, 5, 12.00),
    (4, 2, 9.99),
    (5, 4, 11.25);