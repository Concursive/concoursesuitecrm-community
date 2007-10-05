-- TASK: "Offline Client"
-- NOTE: Added to new_sync.sql 2006-10-30 by holub

ALTER TABLE sync_client ADD COLUMN user_id INT REFERENCES access(user_id);
