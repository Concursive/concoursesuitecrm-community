/* Adds functionality to task so other modules can incorporate tasks */

ALTER TABLE task ADD type INT DEFAULT 1;
UPDATE task SET type = 1;
