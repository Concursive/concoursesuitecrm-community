ALTER TABLE business_process_events ADD process_id INTEGER NULL REFERENCES business_process;

UPDATE business_process_events
SET process_id = business_process.process_id
FROM business_process
WHERE business_process.process_name = task;

ALTER TABLE business_process_events ALTER COLUMN process_id INTEGER NOT NULL;

