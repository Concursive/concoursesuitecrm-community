-- Reorder task loe

UPDATE lookup_task_loe
SET level = 1
WHERE description = 'Minute(s)';

UPDATE lookup_task_loe
SET level = 2
WHERE description = 'Hour(s)';

UPDATE lookup_task_loe
SET level = 3
WHERE description = 'Day(s)';

UPDATE lookup_task_loe
SET level = 4
WHERE description = 'Week(s)';

UPDATE lookup_task_loe
SET level = 5
WHERE description = 'Month(s)';

UPDATE permission_category 
SET category = 'Communications'
WHERE category = 'Campaign Manager';

UPDATE permission_category 
SET category = 'Projects'
WHERE category = 'Project Management';

