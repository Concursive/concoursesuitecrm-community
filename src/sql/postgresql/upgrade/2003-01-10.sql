INSERT INTO system_modules (description) VALUES ('Contacts & Resources');

ALTER TABLE campaign ADD COLUMN reply_addr VARCHAR(255);
ALTER TABLE campaign ADD COLUMN subject VARCHAR(255);
ALTER TABLE campaign ADD COLUMN message TEXT;
ALTER TABLE campaign ADD COLUMN send_method_id INT;

ALTER TABLE campaign ALTER COLUMN reply_addr SET DEFAULT null;
ALTER TABLE campaign ALTER COLUMN subject SET DEFAULT null;
ALTER TABLE campaign ALTER COLUMN message SET DEFAULT null;
ALTER TABLE campaign ALTER COLUMN send_method_id SET DEFAULT -1;

UPDATE campaign SET send_method_id = -1;

ALTER TABLE message ADD COLUMN subject VARCHAR(255);
ALTER TABLE message ALTER COLUMN subject SET DEFAULT null;


CREATE TABLE excluded_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL
);
