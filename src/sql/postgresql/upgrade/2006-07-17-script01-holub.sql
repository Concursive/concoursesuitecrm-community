CREATE TABLE url_map (
  url_id SERIAL PRIMARY KEY,
  time_in_millis NUMERIC(19) NOT NULL,
  url TEXT
);