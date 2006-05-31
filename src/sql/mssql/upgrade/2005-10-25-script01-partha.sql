-- Add a new group_id field to action step table.
ALTER TABLE action_step ADD group_id INTEGER REFERENCES user_group(group_id);
