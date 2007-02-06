ALTER TABLE action_step ADD quick_complete BIT DEFAULT 0;
UPDATE action_step SET quick_complete = 0;
