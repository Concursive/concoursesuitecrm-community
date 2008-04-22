CREATE TABLE call_log_participant(
  participant_id INT AUTO_INCREMENT PRIMARY KEY,
  call_id INT NOT NULL REFERENCES call_log (call_id),
  contact_id INT NOT NULL REFERENCES contact (contact_id),
  is_available INT DEFAULT 1,
  entered timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modified timestamp,
  enteredby INT NOT NULL REFERENCES `access` (user_id),
  modifiedby INT NOT NULL REFERENCES `access` (user_id),
  is_followup INT DEFAULT 0
);

ALTER TABLE call_log ADD COLUMN email_participants BOOLEAN DEFAULT false;
UPDATE call_log SET email_participants = false;

ALTER TABLE call_log ADD COLUMN email_followup_participants BOOLEAN DEFAULT false;
UPDATE call_log SET email_followup_participants = false;

ALTER TABLE call_log DROP COLUMN followup_date;
