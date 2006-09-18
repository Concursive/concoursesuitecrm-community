ALTER TABLE call_log
  ADD COLUMN followup_contact_id INTEGER;

ALTER TABLE call_log
  ADD CONSTRAINT call_log_followup_contact_id_fkey 
  FOREIGN KEY (followup_contact_id)
  REFERENCES contact (contact_id)
  ON UPDATE NO ACTION
  ON DELETE NO ACTION;

CREATE INDEX call_followup_contact_id_idx 
  ON call_log(followup_contact_id);

UPDATE call_log 
 SET followup_contact_id = contact_id
 WHERE followup_contact_id IS NULL AND contact_id IS NOT NULL;
