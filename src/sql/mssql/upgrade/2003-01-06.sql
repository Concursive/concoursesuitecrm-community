/* May 25, 2002  All MSSQL servers up-to-date */

/* May 30, 2002 */

UPDATE autoguide_options SET level = (option_id * 10);

INSERT INTO autoguide_options (option_name, level) VALUES ('CASS', 225);

