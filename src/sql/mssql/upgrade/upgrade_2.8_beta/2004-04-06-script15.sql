/* To alter the data type of column contract_value from DECIMAL to float  */
ALTER TABLE ticket_csstm_form
ADD travel_towards_sc BIT DEFAULT 1;

ALTER TABLE ticket_csstm_form
ADD labor_towards_sc BIT DEFAULT 1;

ALTER TABLE ticket_activity_item
ADD travel_hours INT;

ALTER TABLE ticket_activity_item
ADD travel_minutes INT;

ALTER TABLE ticket_activity_item
ADD labor_hours INT;

ALTER TABLE ticket_activity_item
ADD labor_minutes INT;
