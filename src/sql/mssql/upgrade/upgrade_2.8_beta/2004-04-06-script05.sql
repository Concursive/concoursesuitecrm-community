/* In service_contracts Set current start date to initial start date if it is null */
UPDATE service_contract 
SET current_start_date = initial_start_date 
WHERE current_start_date is null;

