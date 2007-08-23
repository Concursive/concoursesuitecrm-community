CREATE SEQUENCE call_log_participant_participant_id_seq;

CREATE TABLE call_log_participant
(
  participant_id int4 NOT NULL DEFAULT nextval('call_log_participant_participant_id_seq'::regclass),
  call_id int4 NOT NULL,
  contact_id int4 NOT NULL,
  is_available int4 DEFAULT 1,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby int4 NOT NULL,
  modifiedby int4 NOT NULL,
  is_followup int4 DEFAULT 0,
  CONSTRAINT call_log_participant_pkey PRIMARY KEY (participant_id),
  CONSTRAINT call_log_participant_call_id_fkey FOREIGN KEY (call_id)
      REFERENCES call_log (call_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT call_log_participant_contact_id_fkey FOREIGN KEY (contact_id)
      REFERENCES contact (contact_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT call_log_participant_enteredby_fkey FOREIGN KEY (enteredby)
      REFERENCES "access" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT call_log_participant_modifiedby_fkey FOREIGN KEY (enteredby)
      REFERENCES "access" (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 

ALTER TABLE call_log ADD COLUMN email_participants BOOLEAN;
UPDATE call_log SET email_participants = false;
ALTER TABLE call_log ALTER COLUMN email_participants SET DEFAULT false;

ALTER TABLE call_log ADD COLUMN email_followup_participants BOOLEAN;
UPDATE call_log SET email_followup_participants = false;
ALTER TABLE call_log ALTER COLUMN email_followup_participants SET DEFAULT false;

ALTER TABLE call_log DROP COLUMN followup_date;
