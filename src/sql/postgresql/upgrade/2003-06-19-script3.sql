/* Once the business processes have been loaded, execute this script
   to join the business_process and business_process_event tables */
   
ALTER TABLE business_process_events ADD COLUMN process_id INTEGER;

UPDATE business_process_events
SET process_id = business_process.process_id
FROM business_process
WHERE business_process.process_name = task;

ALTER TABLE business_process_events ADD CONSTRAINT business_process_not_null CHECK(process_id IS NOT NULL) ;
ALTER TABLE business_process_events ADD FOREIGN KEY (process_id) REFERENCES business_process(process_id);


