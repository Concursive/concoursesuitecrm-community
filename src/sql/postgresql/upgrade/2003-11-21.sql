/* Adds ordering feature to the help features */
ALTER TABLE help_features ADD COLUMN level INT;
UPDATE help_features SET level = 0;
ALTER TABLE help_features ALTER COLUMN level SET DEFAULT 0;
