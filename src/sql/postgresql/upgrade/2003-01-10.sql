/* 2-4-2002 Upgrade access table to support alias feature */

ALTER TABLE access ADD COLUMN alias INT DEFAULT -1;
ALTER TABLE access ADD COLUMN assistant INT DEFAULT -1;


