-- Alter access to add a new column called hidden
ALTER TABLE access ADD hidden BIT DEFAULT 0;
UPDATE access SET hidden = 0;

