/* To alter the data type of column travel_time, labor_time from DECIMAL to float  */
EXEC sp_rename 'ticket_activity_item.travel_time', 'travel_time_to_delete', 'COLUMN';

ALTER TABLE ticket_activity_item 
ADD travel_time float;

UPDATE ticket_activity_item
SET travel_time = travel_time_to_delete;

ALTER TABLE ticket_activity_item
DROP COLUMN travel_time_to_delete;


EXEC sp_rename 'ticket_activity_item.labor_time', 'labor_time_to_delete', 'COLUMN';

ALTER TABLE ticket_activity_item 
ADD labor_time float;

UPDATE ticket_activity_item
SET labor_time = labor_time_to_delete;

ALTER TABLE ticket_activity_item
DROP COLUMN labor_time_to_delete;
