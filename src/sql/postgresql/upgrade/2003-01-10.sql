/* December 6, 2002 */

DROP TABLE mod_log;

CREATE TABLE mod_log (
  mod_id SERIAL PRIMARY KEY,
  entered TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NULL,
  action INT NOT NULL,
  record_id INT NULL,
  record_size INT NULL
);


