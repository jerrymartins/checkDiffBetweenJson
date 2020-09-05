CREATE TABLE IF NOT EXISTS comparison (
    id SERIAL,
    left_data VARCHAR NOT NULL,
    right_data VARCHAR NOT NULL,
    only_left_fields TEXT [],
    only_right_fields TEXT [],
    different_fields TEXT
);