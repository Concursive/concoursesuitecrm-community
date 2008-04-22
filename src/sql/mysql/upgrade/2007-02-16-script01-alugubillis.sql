-- Adding few fields  to knowledge_base table
  
ALTER TABLE knowledge_base DROP COLUMN status;
ALTER TABLE knowledge_base ADD COLUMN status INTEGER NOT NULL default 1 REFERENCES lookup_kb_status(code);
UPDATE knowledge_base SET status = 1;


-- Adding  field  tocfsinbox_messagelink table

ALTER TABLE cfsinbox_messagelink ADD COLUMN replyto varchar(255) ;



-- Inserting the Record into    lookup_kb_status table
-- Done in .bsh
--INSERT INTO lookup_kb_status(description,constant_id) VALUES('Draft',1);
--INSERT INTO lookup_kb_status(description, constant_id) VALUES('Approved',2);


