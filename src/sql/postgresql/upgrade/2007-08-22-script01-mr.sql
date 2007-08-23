
-- TODO: Fix this script to alter the following columns and data
ALTER TABLE business_process_parameter_library ALTER COLUMN default_value TEXT;
ALTER TABLE business_process_parameter ALTER COLUMN param_value TEXT;
ALTER TABLE business_process_component_parameter ALTER COLUMN param_value TEXT;
