--modifications for the organization table
ALTER TABLE organization ADD COLUMN alertdate_timezone VARCHAR(255);
ALTER TABLE organization ADD COLUMN contract_end_timezone VARCHAR(255);

--modifications for the task table
ALTER TABLE task ADD COLUMN duedate_timezone VARCHAR(255);

--modifications for the ticket table
ALTER TABLE ticket ADD COLUMN est_resolution_date_timezone VARCHAR(255);

--modifications for the opportunity_component table
ALTER TABLE opportunity_component ADD COLUMN alertdate_timezone VARCHAR(255);
