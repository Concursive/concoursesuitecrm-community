INSERT INTO system_modules (description) VALUES ('Contacts & Resources');

CREATE TABLE excluded_recipient (
  id serial PRIMARY KEY,
  campaign_id INT NOT NULL DEFAULT -1,
  contact_id INT NOT NULL
);
