ALTER TABLE action_item_work_notes ADD COLUMN submittedby INTEGER REFERENCES access(user_id);
UPDATE action_item_work_notes SET submittedby = (SELECT owner FROM action_item_work aiw WHERE action_item_work_notes.item_work_id = aiw.item_work_id);
ALTER TABLE action_item_work_notes ALTER COLUMN submittedby SET NOT NULL;
