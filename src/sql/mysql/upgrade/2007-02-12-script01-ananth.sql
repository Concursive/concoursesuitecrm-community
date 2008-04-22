-- missing id field in action_step_account_types
-- TODO: Verify script on existing systems. Primary Key Data?

ALTER TABLE action_step_account_types ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY;