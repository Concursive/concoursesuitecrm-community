ALTER TABLE ticket_csstm_form DROP COLUMN form_type;
ALTER TABLE ticket_activity_item DROP COLUMN labor_time;
ALTER TABLE ticket_activity_item DROP COLUMN travel_time;
ALTER TABLE service_contract DROP COLUMN total_hours_purchased;

DELETE FROM lookup_lists_lookup WHERE table_name = 'lookup_ticket_form';

-- Following line will cause an error because of foreign_key, delete it manually
ALTER TABLE ticket DROP COLUMN form_type;

