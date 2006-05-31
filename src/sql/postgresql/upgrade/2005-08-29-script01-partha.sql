-- Upgrade script to update the default accessType for USF2

UPDATE lookup_access_types set default_item=false WHERE rule_id=626030334 AND link_module_id=804051057;
UPDATE lookup_access_types SET default_item=true WHERE rule_id=626030335 AND link_module_id=804051057;

UPDATE opportunity_component SET terms=1 WHERE units='W';

-- Changes to Action Plan

UPDATE action_phase SET description = 'Pre-Planning' WHERE phase_name = 'Stage 1';

UPDATE action_phase SET description = 'Needs Assessment' WHERE phase_name = 'Stage 2';

UPDATE action_phase SET description = '1st Benefits Presentation' WHERE phase_name = 'Stage 3';

UPDATE action_phase SET description = 'Feedback and Follow-up' WHERE phase_name = 'Stage 4';

UPDATE action_phase SET description = 'New Customer Start-up' WHERE phase_name = 'Stage 5';

UPDATE action_step SET action_id = 8 WHERE description = 'Primary Supplier';

INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Appert\'s Foodservice', 10);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Hawkeye Foodservice', 20);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Performance Food Group', 30);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Reinhart FoodService', 40);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Sysco', 50);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Upper Lakes Foods', 60);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Vistar', 70);
INSERT INTO action_step_lookup (step_id, description, level) VALUES ((SELECT max(step_id) FROM action_step WHERE description = 'Primary Supplier'), 'Other', 80);

UPDATE action_step SET action_id = 6 WHERE description = 'Interview Scheduled';

UPDATE action_step SET action_id = 6 WHERE description = 'Benefits Presentation Scheduled';

INSERT INTO action_step (description, action_id, phase_id, parent_id, enabled, permission_type) VALUES ('Follow-up Notes', 7, (SELECT max(phase_id) FROM action_phase WHERE phase_name = 'Stage 4'), (SELECT max(step_id) FROM action_step WHERE description = 'Prospect Priority'), true, 1);

UPDATE action_step SET description = 'Order Requested and Credit Application Received', action_id = 2 WHERE description = 'Order Requested';

UPDATE action_step SET description = 'Payment Terms Finalized' WHERE description = 'Payment Identified';

UPDATE action_step SET description = 'First Order Placed for Delivery', action_id = 6 WHERE description = 'First Order Delivered';

UPDATE action_step SET action_id = 9 WHERE description = 'Prospect Data Accurate';

-- Archive this plan and import the next so that the work does not have to be modified

UPDATE action_plan SET enabled = false, archive_date = CURRENT_TIMESTAMP, description = 'Individual Prospect (v1.0)' WHERE plan_id = 1;


