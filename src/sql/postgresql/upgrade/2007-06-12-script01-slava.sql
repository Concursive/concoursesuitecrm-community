ALTER TABLE ticket ADD COLUMN submitter_id INT REFERENCES organization;
ALTER TABLE ticket ADD COLUMN submitter_contact_id INT REFERENCES contact;

update ticket set submitter_id = org_id, submitter_contact_id = contact_id;
