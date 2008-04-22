
-- Change the datatype to TEXT
ALTER TABLE business_process_parameter_library ADD COLUMN default_value_temp VARCHAR(4000);
UPDATE business_process_parameter_library SET default_value_temp = default_value;
ALTER TABLE business_process_parameter_library DROP default_value;
ALTER TABLE business_process_parameter_library ADD COLUMN default_value TEXT;
UPDATE business_process_parameter_library SET default_value = default_value_temp;
ALTER TABLE business_process_parameter_library DROP default_value_temp;

ALTER TABLE business_process_parameter ADD COLUMN param_value_temp VARCHAR(4000);
UPDATE business_process_parameter SET param_value_temp = param_value;
ALTER TABLE business_process_parameter DROP param_value;
ALTER TABLE business_process_parameter ADD COLUMN param_value TEXT;
UPDATE business_process_parameter SET param_value = param_value_temp;
ALTER TABLE business_process_parameter DROP param_value_temp;

ALTER TABLE business_process_component_parameter ADD COLUMN param_value_temp VARCHAR(4000);
UPDATE business_process_component_parameter SET param_value_temp = param_value;
ALTER TABLE business_process_component_parameter DROP param_value;
ALTER TABLE business_process_component_parameter ADD COLUMN param_value TEXT;
UPDATE business_process_component_parameter SET param_value = param_value_temp;
ALTER TABLE business_process_component_parameter DROP param_value_temp;
