/* May 16, 2002  All servers up-to-date */

CREATE TABLE access_log (
  id serial,
  user_id INT NOT NULL DEFAULT -1,
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(15),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
