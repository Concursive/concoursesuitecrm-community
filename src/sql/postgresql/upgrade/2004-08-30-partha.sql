-- table time zone fields
ALTER TABLE opportunity_component ADD COLUMN closedate_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN initial_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN current_start_date_timezone VARCHAR(255);
ALTER TABLE service_contract ADD COLUMN current_end_date_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN date_listed_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN expiration_date_timezone VARCHAR(255);
ALTER TABLE asset ADD COLUMN purchase_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN assigned_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket_activity_item ADD COLUMN activity_date_timezone VARCHAR(255);
ALTER TABLE campaign ADD COLUMN active_date_timezone VARCHAR(255);
ALTER TABLE project_news ADD COLUMN start_date_timezone VARCHAR(255);
ALTER TABLE project_news ADD COLUMN end_date_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD COLUMN startdate_timezone VARCHAR(255);
ALTER TABLE project_requirements ADD COLUMN deadline_timezone VARCHAR(255);
ALTER TABLE project_assignments ADD COLUMN due_date_timezone VARCHAR(255);
ALTER TABLE projects ADD COLUMN requestDate_timezone VARCHAR(255);
ALTER TABLE projects ADD COLUMN est_closedate_timezone VARCHAR(255);
ALTER TABLE ticket_csstm_form ADD COLUMN alert_date_timezone VARCHAR(255);

