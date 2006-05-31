-- Add a group id to the action step table to store the user group information.
ALTER TABLE action_step ADD COLUMN group_id INTEGER REFERENCES user_group(group_id);
