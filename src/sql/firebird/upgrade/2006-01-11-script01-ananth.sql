ALTER TABLE lookup_step_actions ADD constant_id INT NOT NULL UNIQUE;
--Add constant values
UPDATE lookup_step_actions SET constant_id = 110061030 WHERE description='Require user to attach or create an opportunity';
UPDATE lookup_step_actions SET constant_id = 110061031 WHERE description='Require user to attach or upload a document';
UPDATE lookup_step_actions SET constant_id = 110061032 WHERE description='Require user to attach or create an activity';
UPDATE lookup_step_actions SET constant_id = 110061033 WHERE description='Require user to update a specific action plan folder';
UPDATE lookup_step_actions SET constant_id = 110061034 WHERE description='Require user to update the Rating';
UPDATE lookup_step_actions SET constant_id = 110061034 WHERE description='Require user to update the rating';
UPDATE lookup_step_actions SET constant_id = 110061035 WHERE description='Require user to attach or create a single Note';
UPDATE lookup_step_actions SET constant_id = 110061035 WHERE description='Require user to attach or create a single note';
UPDATE lookup_step_actions SET constant_id = 110061036 WHERE description='Require user to attach or create multiple Notes';
UPDATE lookup_step_actions SET constant_id = 110061036 WHERE description='Require user to attach or create multiple notes';
UPDATE lookup_step_actions SET constant_id = 110061037 WHERE description='Require user to attach or create a Lookup List';
UPDATE lookup_step_actions SET constant_id = 110061038 WHERE description='View Account';
UPDATE lookup_step_actions SET constant_id = 110061038 WHERE description='Allow user to view the account';
UPDATE lookup_step_actions SET constant_id = 110061039 WHERE description='Require user to attach or create an account contact';
UPDATE lookup_step_actions SET constant_id = 110061040 WHERE description='Require user to create account relationships';

--Fix step_action_map to use 'Action' constants
ALTER TABLE step_action_map ADD action_constant_id INTEGER REFERENCES lookup_step_actions(constant_id);
UPDATE step_action_map SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE step_action_map.action_id = lsa.code);

-- can not be done via normal ddl - research how to do this via system tables
--ALTER TABLE step_action_map ALTER COLUMN action_constant_id SET NOT NULL;

ALTER TABLE step_action_map DROP action_id;

--Fix action_step to use 'Action' constants
--Create a temp column 'action_constant_id' and copy action constants based on existing action_step.action_id values
ALTER TABLE action_step ADD action_constant_id INTEGER;
UPDATE action_step SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE action_step.action_id = lsa.code);
--Drop column and its references
ALTER TABLE action_step DROP action_id;
ALTER TABLE action_step ADD action_id INTEGER;
UPDATE action_step SET action_id = (SELECT action_constant_id FROM action_step asp WHERE action_step.step_id = asp.step_id);
--Drop temp column
ALTER TABLE action_step DROP action_constant_id;



