ALTER TABLE call_log
  ADD COLUMN followup_contact_id int4 REFERENCES contact(contact_id);


CREATE INDEX call_fcontact_id_idx
  ON call_log
  USING btree
  (followup_contact_id);

UPDATE call_log 
 SET followup_contact_id = contact_id
 WHERE followup_contact_id IS NULL AND contact_id IS NOT NULL;
