/* Adds functionality to task so other modules can incorporate tasks */

ALTER TABLE task ADD COLUMN type INT;
ALTER TABLE task ALTER type SET DEFAULT 1;
UPDATE task SET type = 1;
