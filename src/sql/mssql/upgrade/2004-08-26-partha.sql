--modifications for the organization table
ALTER TABLE organization ADD alertdate_timezone VARCHAR(255);
ALTER TABLE organization ADD contract_end_timezone VARCHAR(255);

--modifications for the task table
ALTER TABLE task ADD duedate_timezone VARCHAR(255);

--modifications for the ticket table
ALTER TABLE ticket ADD est_resolution_date_timezone VARCHAR(255);

--modifications for the opportunity_component table
ALTER TABLE opportunity_component ADD alertdate_timezone VARCHAR(255);
