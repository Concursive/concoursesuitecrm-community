CREATE SEQUENCE url_map_url_id_seq;
CREATE TABLE url_map (
  url_id INT PRIMARY KEY,
  time_in_millis DECIMAL NOT NULL,
  url CLOB
);
