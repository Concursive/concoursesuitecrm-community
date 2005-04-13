-- Alter quote_entry table to add the opportunity and modify the existing org_id column.
ALTER TABLE quote_entry ADD COLUMN opp_id INTEGER REFERENCES opportunity_header(opp_id);
ALTER TABLE quote_entry ALTER COLUMN opp_id SET NOT NULL;

-- Alter quote_entry to add a new column called version
ALTER TABLE quote_entry ADD COLUMN "version" VARCHAR(255);
ALTER TABLE quote_entry ALTER COLUMN "version" SET DEFAULT '0';
UPDATE quote_entry SET version = '1.0'; 
ALTER TABLE quote_entry ALTER COLUMN "version" SET NOT NULL;

-- Create a new table to group quote versions
CREATE TABLE quote_group (
  group_id SERIAL PRIMARY KEY,
  unused CHAR(1) NULL
);

-- Alter quote_entry to add a new column group_id
ALTER TABLE quote_entry ADD COLUMN group_id INTEGER REFERENCES quote_group(group_id);
ALTER TABLE quote_entry ALTER COLUMN group_id SET NOT NULL;

