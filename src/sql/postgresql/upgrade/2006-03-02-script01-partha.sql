ALTER TABLE action_step ADD COLUMN campaign_id INTEGER REFERENCES campaign(campaign_id);
ALTER TABLE action_step ADD COLUMN allow_duplicate_recipient boolean;
UPDATE action_step SET allow_duplicate_recipient = false;
ALTER TABLE action_step ALTER COLUMN allow_duplicate_recipient SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_duplicate_recipient SET DEFAULT false;
