ALTER TABLE lookup_step_actions ADD COLUMN constant_id INT;
--Add constant values
UPDATE lookup_step_actions SET constant_id = 110061030 WHERE description='Require user to attach or create an opportunity';      
UPDATE lookup_step_actions SET constant_id = 110061031 WHERE description='Require user to attach or upload a document';          
UPDATE lookup_step_actions SET constant_id = 110061032 WHERE description='Require user to attach or create an activity';         
UPDATE lookup_step_actions SET constant_id = 110061033 WHERE description='Require user to update a specific action plan folder'; 
UPDATE lookup_step_actions SET constant_id = 110061034 WHERE description='Require user to update the Rating';                    
UPDATE lookup_step_actions SET constant_id = 110061035 WHERE description='Require user to attach or create a single Note';       
UPDATE lookup_step_actions SET constant_id = 110061036 WHERE description='Require user to attach or create multiple Notes';      
UPDATE lookup_step_actions SET constant_id = 110061037 WHERE description='Require user to attach or create a Lookup List';       
UPDATE lookup_step_actions SET constant_id = 110061038 WHERE description='View Account';                                         
UPDATE lookup_step_actions SET constant_id = 110061039 WHERE description='Require user to attach or create an account contact';
UPDATE lookup_step_actions SET constant_id = 110061040 WHERE description='Require user to create account relationships';
--Set constraint
ALTER TABLE lookup_step_actions ALTER COLUMN constant_id SET NOT NULL;
ALTER TABLE lookup_step_actions ADD CONSTRAINT lookup_step_constant_id_key UNIQUE (constant_id);

--Fix step_action_map to use 'Action' constants
ALTER TABLE step_action_map ADD COLUMN action_constant_id INT REFERENCES lookup_step_actions(constant_id);
UPDATE step_action_map SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE step_action_map.action_id = lsa.code); 
ALTER TABLE step_action_map ALTER COLUMN action_constant_id SET NOT NULL;
ALTER TABLE step_action_map DROP COLUMN action_id;

--Fix action_step to use 'Action' constants
--Create a temp column 'action_constant_id' and copy action constants based on existing action_step.action_id values
ALTER TABLE action_step ADD COLUMN action_constant_id INT;
UPDATE action_step SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE action_step.action_id = lsa.code);
--Drop column and its references
ALTER TABLE action_step DROP COLUMN action_id;
ALTER TABLE action_step ADD COLUMN action_id INT;
UPDATE action_step SET action_id = (SELECT action_constant_id FROM action_step asp WHERE action_step.step_id = asp.step_id);
--Drop temp column
ALTER TABLE action_step DROP COLUMN action_constant_id;




