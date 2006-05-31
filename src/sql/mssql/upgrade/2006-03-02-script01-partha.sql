ALTER TABLE action_step ADD campaign_id INT REFERENCES campaign(campaign_id);
ALTER TABLE action_step ADD allow_duplicate_recipient BIT DEFAULT 0 NOT NULL;
UPDATE action_step SET allow_duplicate_recipient = 0;
