-- Update permission_category to enable folders for Employees
UPDATE permission_category SET folders = true WHERE category = 'Employees';
