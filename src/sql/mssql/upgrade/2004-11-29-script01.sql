-- Enable inserting folder data through XML API
ALTER TABLE quote_entry ADD opp_id INTEGER REFERENCES opportunity_header(opp_id);

-- Alter quote_entry to remove the NOT NULL Constraint for org_id 
ALTER TABLE quote_entry ALTER COLUMN org_id INTEGER NULL;

-- Alter quote_entry to add a new column called version
ALTER TABLE quote_entry ADD "version" VARCHAR(255);
ALTER TABLE quote_entry ALTER COLUMN version SET DEFAULT '0';
UPDATE quote_entry SET version='1.0';
ALTER TABLE quote_entry ALTER COLUMN version SET NOT NULL;

-- Create a new table to group quote versions
CREATE TABLE quote_group (
  group_id INTEGER IDENTITY PRIMARY KEY,
  unused CHAR(1) NULL
);

-- Alter quote_entry to add a new column group_id
ALTER TABLE quote_entry ADD group_id INTEGER REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ALTER COLUMN group_id SET NOT NULL;
