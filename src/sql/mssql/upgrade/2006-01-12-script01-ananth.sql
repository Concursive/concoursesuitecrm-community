ALTER TABLE action_phase ADD global BIT DEFAULT 0;
UPDATE action_phase SET global = 0;
