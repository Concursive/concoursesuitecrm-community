ALTER TABLE action_phase ADD "global" CHAR(1) DEFAULT 'N';
UPDATE action_phase SET "global" = 'N';
