ALTER TABLE call_log ADD followup_end_date TIMESTAMP NULL;
ALTER TABLE call_log ADD followup_end_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD followup_location VARCHAR(255);
ALTER TABLE call_log ADD followup_length INT;
ALTER TABLE call_log ADD followup_length_duration INT REFERENCES lookup_call_reminder(code);

ALTER TABLE call_log ADD call_start_date TIMESTAMP NULL;
ALTER TABLE call_log ADD call_start_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD call_end_date TIMESTAMP NULL;
ALTER TABLE call_log ADD call_end_date_timezone VARCHAR(255);
ALTER TABLE call_log ADD call_location VARCHAR(255);
ALTER TABLE call_log ADD call_length_duration INT REFERENCES lookup_call_reminder(code);
