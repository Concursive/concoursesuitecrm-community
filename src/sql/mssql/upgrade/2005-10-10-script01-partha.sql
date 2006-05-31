ALTER TABLE action_phase ADD random BIT DEFAULT 0;
UPDATE action_phase SET random = 0;
