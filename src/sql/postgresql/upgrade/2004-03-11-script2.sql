
-- Rename ticket priorities

UPDATE ticket_priority
SET description = 'Urgent'
WHERE description = 'Next';

UPDATE ticket_priority
SET description = 'As Scheduled'
WHERE description = 'Scheduled';

UPDATE ticket_priority
SET description = 'Critical'
WHERE description = 'Immediate';

