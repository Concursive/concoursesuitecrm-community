--Alter the quotelog to add an issued_date field
ALTER TABLE quotelog ADD COLUMN issued_date TIMESTAMP(3);
--ALTER TABLE quotelog ALTER COLUMN issued_date SET DEFAULT CURRENT_TIMESTAMP;

-- Update and enable permissions
UPDATE permission 
SET enabled = true, 
permission_add = false, 
permission_edit = false, 
permission_delete = false 
WHERE permission = 'quotes';

UPDATE permission SET enabled = true WHERE permission = 'accounts-quotes';

UPDATE permission_category SET enabled = true WHERE category = 'Quotes';

ALTER TABLE quote_entry ALTER COLUMN opp_id DROP NOT NULL;

UPDATE lookup_quote_status SET level = (code * 10);

--  2004-11-09

-- Add the submit_action field to quote and quotelog tables
ALTER TABLE quote_entry ADD COLUMN submit_action INTEGER;
ALTER TABLE quotelog ADD COLUMN submit_action INTEGER;

ALTER TABLE quote_entry ADD COLUMN closed TIMESTAMP(3);
