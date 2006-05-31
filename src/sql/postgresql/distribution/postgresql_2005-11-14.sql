UPDATE access SET hidden = false WHERE hidden IS NULL;

CREATE TABLE ticket_category_draft_assignment (
  map_id SERIAL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE TABLE ticket_category_assignment (
  map_id SERIAL PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

ALTER TABLE permission_category ADD COLUMN custom_list_views BOOLEAN;
UPDATE permission_category SET custom_list_views = false;
ALTER TABLE permission_category ALTER COLUMN custom_list_views SET NOT NULL;
ALTER TABLE permission_category ALTER COLUMN custom_list_views SET DEFAULT FALSE;

UPDATE permission_category SET custom_list_views = true WHERE constant = 8;

CREATE TABLE custom_list_view_editor (
  editor_id SERIAL PRIMARY KEY,
  module_id INT NOT NULL REFERENCES permission_category(category_id),
  constant_id INT NOT NULL,
  description TEXT,
  level INT default 0,
  category_id INT NOT NULL
);

CREATE TABLE custom_list_view (
  view_id SERIAL PRIMARY KEY,
  editor_id INT NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description TEXT,
  is_default BOOLEAN DEFAULT FALSE,
  entered TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE custom_list_view_field (
  field_id SERIAL PRIMARY KEY,
  view_id INT NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);

ALTER TABLE ticket_defect ADD COLUMN site_id INT REFERENCES lookup_site_id(code);

ALTER TABLE user_group ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE action_plan ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE import ADD COLUMN site_id INT REFERENCES lookup_site_id(code);

CREATE SEQUENCE lookup_ticket_state_code_seq;
CREATE TABLE lookup_ticket_state (
  code INTEGER DEFAULT nextval('lookup_ticket_state_code_seq') NOT NULL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
	enabled BOOLEAN DEFAULT true
);

ALTER TABLE ticket ADD COLUMN state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticketlog ADD COLUMN state_id INT REFERENCES lookup_ticket_state(code);

ALTER TABLE ticket_category_draft ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE ticket_category ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE asset_category_draft ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE asset_category ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE action_plan_category_draft ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE action_plan_category ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE contact ADD site_id INT REFERENCES lookup_site_id(code);

UPDATE contact
SET site_id = organization.site_id
FROM organization
WHERE contact.org_id = organization.org_id
AND contact.org_id > 0;

UPDATE contact
SET site_id = access.site_id
FROM access
WHERE contact.user_id = access.user_id
AND contact.org_id = 0
AND contact.user_id > 0
AND contact.site_id IS NULL;

UPDATE contact
SET site_id = import.site_id
FROM import
WHERE contact.import_id = import.import_id
AND contact.site_id IS NULL;

UPDATE contact
SET site_id = access.site_id
FROM access
WHERE contact.enteredby = access.user_id
AND contact.import_id IS NULL
AND contact.site_id IS NULL;

alter table ticket add site_id int references lookup_site_id(code);

UPDATE ticket
SET site_id = organization.site_id
FROM organization
WHERE ticket.org_id = organization.org_id
AND ticket.org_id > 0;

ALTER TABLE lookup_step_actions ADD COLUMN constant_id INT;
