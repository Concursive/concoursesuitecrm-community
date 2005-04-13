-- Fix broken activities that were marked as completed

UPDATE call_log
SET status_id = 2
WHERE call_id IN (SELECT parent_id FROM call_log)
AND status_id = 3;
