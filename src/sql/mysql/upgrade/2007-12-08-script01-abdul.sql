--- adding four new fields to organization table

ALTER TABLE organization ADD COLUMN no_phone BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_fax BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_mail BOOLEAN DEFAULT false;
ALTER TABLE organization ADD COLUMN no_email BOOLEAN DEFAULT false;



  