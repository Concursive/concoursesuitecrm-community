ALTER TABLE action_step ADD campaign_id INTEGER REFERENCES campaign(campaign_id);
ALTER TABLE action_step ADD allow_duplicate_recipient CHAR(1) DEFAULT 'N' NOT NULL;
UPDATE action_step SET allow_duplicate_recipient = 'N';