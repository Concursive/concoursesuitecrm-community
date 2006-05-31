-- Update permission_category to enable folders for Employees
UPDATE permission_category SET folders = 1 WHERE category = 'Employees';
