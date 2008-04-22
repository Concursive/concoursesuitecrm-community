
ALTER TABLE ticket ADD COLUMN submitter_id INT REFERENCES organization;
ALTER TABLE ticket ADD COLUMN submitter_contact_id INT REFERENCES contact;

--postponed this until the application is updated with the submitter functionality
--update ticket set submitter_id = org_id, submitter_contact_id = contact_id;

ALTER TABLE service_contract ADD COLUMN submitter_id INT REFERENCES organization;
