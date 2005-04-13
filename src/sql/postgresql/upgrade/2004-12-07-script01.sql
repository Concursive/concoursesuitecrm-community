-- Add the state field to access
ALTER TABLE access ADD COLUMN hidden boolean;
UPDATE access set hidden = false;
ALTER TABLE access ALTER COLUMN hidden SET DEFAULT false;
