/* 2-4-2002 Upgrade access table to support alias feature */

ALTER TABLE access ADD COLUMN alias INT DEFAULT -1;
ALTER TABLE access ADD COLUMN assistant INT DEFAULT -1;

ALTER TABLE access ALTER COLUMN alias SET DEFAULT -1;
ALTER TABLE access ALTER COLUMN assistant SET DEFAULT -1;

UPDATE access SET alias = -1;
UPDATE access SET assistant = -1;

