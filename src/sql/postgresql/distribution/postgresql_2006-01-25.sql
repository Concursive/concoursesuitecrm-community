ALTER TABLE opportunity_header ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

UPDATE opportunity_header
SET site_id = organization.site_id
FROM organization
WHERE opportunity_header.acctlink = organization.org_id
AND opportunity_header.acctlink > -1
AND opportunity_header.acctlink IS NOT NULL;

UPDATE opportunity_header
SET site_id = contact.site_id
FROM contact
WHERE opportunity_header.contactlink = contact.contact_id
AND opportunity_header.contactlink > -1
AND opportunity_header.contactlink IS NOT NULL;

ALTER TABLE document_store_role_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_user_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE document_store_department_member ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

UPDATE document_store_user_member SET site_id = (SELECT site_id FROM access ac WHERE ac.user_id = item_id);

UPDATE lookup_im_services SET description = 'Jabber' where description = 'Jabber Instant Messenger';
UPDATE lookup_im_services SET description = 'AIM' where description = 'AOL Instant Messenger';
UPDATE lookup_im_services SET description = 'MSN' where description = 'MSN Instant Messenger';

ALTER TABLE action_step ADD COLUMN target_relationship VARCHAR(80);

CREATE TABLE action_step_account_types (
  step_id INTEGER NOT NULL REFERENCES action_step(step_id),
  type_id INTEGER NOT NULL REFERENCES lookup_account_types(code)
);

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
ALTER TABLE lookup_step_actions ALTER COLUMN constant_id SET NOT NULL;
ALTER TABLE lookup_step_actions ADD CONSTRAINT lookup_step_constant_id_key UNIQUE (constant_id);

ALTER TABLE step_action_map ADD COLUMN action_constant_id INT REFERENCES lookup_step_actions(constant_id);
UPDATE step_action_map SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE step_action_map.action_id = lsa.code);
ALTER TABLE step_action_map ALTER COLUMN action_constant_id SET NOT NULL;
ALTER TABLE step_action_map DROP COLUMN action_id;

UPDATE action_step SET action_id = 10 WHERE action_id = 0;
ALTER TABLE action_step ADD COLUMN action_constant_id INT;
UPDATE action_step SET action_constant_id = (SELECT constant_id FROM lookup_step_actions lsa WHERE action_step.action_id = lsa.code);
ALTER TABLE action_step DROP COLUMN action_id;
ALTER TABLE action_step ADD COLUMN action_id INT;
UPDATE action_step SET action_id = action_constant_id;
ALTER TABLE action_step DROP COLUMN action_constant_id;

INSERT INTO action_plan_constants (constant_id, description) VALUES(110061020, 'relationship');

ALTER TABLE action_phase ADD COLUMN global BOOLEAN;
ALTER TABLE action_phase ALTER COLUMN global SET DEFAULT false;
UPDATE action_phase SET global = false;

ALTER TABLE action_plan_work_notes ADD COLUMN description2 VARCHAR(4096);
UPDATE action_plan_work_notes SET description2 = description;
ALTER TABLE action_plan_work_notes DROP COLUMN description;
ALTER TABLE action_plan_work_notes ADD COLUMN description VARCHAR(4096);
UPDATE action_plan_work_notes SET description = description2;
ALTER TABLE action_plan_work_notes DROP COLUMN description2;
ALTER TABLE action_plan_work_notes ALTER description SET NOT NULL;

ALTER TABLE action_item_work_notes ADD COLUMN description2 VARCHAR(4096);
UPDATE action_item_work_notes SET description2 = description;
ALTER TABLE action_item_work_notes DROP COLUMN description;
ALTER TABLE action_item_work_notes ADD COLUMN description VARCHAR(4096);
UPDATE action_item_work_notes SET description = description2;
ALTER TABLE action_item_work_notes DROP COLUMN description2;
ALTER TABLE action_item_work_notes ALTER description SET NOT NULL;


ALTER TABLE action_step ADD COLUMN allow_update BOOLEAN;
UPDATE action_step SET allow_update = true;
ALTER TABLE action_step ALTER COLUMN allow_update SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_update SET DEFAULT TRUE;

ALTER TABLE action_step ALTER COLUMN action_id DROP NOT NULL;
