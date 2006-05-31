UPDATE access SET hidden = 0 WHERE hidden IS NULL;

CREATE TABLE ticket_category_draft_assignment (
  map_id INT IDENTITY PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category_draft(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

CREATE TABLE ticket_category_assignment (
  map_id INT IDENTITY PRIMARY KEY,
  category_id INTEGER NOT NULL REFERENCES ticket_category(id),
  department_id INTEGER REFERENCES lookup_department(code),
  assigned_to INTEGER REFERENCES access(user_id),
  group_id INTEGER REFERENCES user_group(group_id)
);

ALTER TABLE permission_category ADD custom_list_views BIT NOT NULL DEFAULT 0;
UPDATE permission_category SET custom_list_views = 0;

UPDATE permission_category SET custom_list_views = 1 WHERE constant = 8;

CREATE TABLE custom_list_view_editor (
  editor_id INT IDENTITY PRIMARY KEY,
  module_id INTEGER NOT NULL REFERENCES permission_category(category_id),
  constant_id INTEGER NOT NULL,
  description TEXT,
  level INTEGER default 0,
  category_id INT NOT NULL
);

CREATE TABLE custom_list_view (
  view_id INT IDENTITY PRIMARY KEY,
  editor_id INTEGER NOT NULL REFERENCES custom_list_view_editor(editor_id),
  name VARCHAR(80) NOT NULL,
  description TEXT,
  is_default BIT DEFAULT 0,
  entered DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE custom_list_view_field (
  field_id INT IDENTITY PRIMARY KEY,
  view_id INTEGER NOT NULL REFERENCES custom_list_view(view_id),
  name VARCHAR(80) NOT NULL
);

ALTER TABLE ticket_defect ADD site_id INT REFERENCES lookup_site_id(code);

ALTER TABLE user_group ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE action_plan ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE import ADD site_id INT REFERENCES lookup_site_id(code);

CREATE TABLE lookup_ticket_state (
  code INTEGER IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
	enabled BIT DEFAULT 1
);

ALTER TABLE ticket ADD state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticketlog ADD state_id INT REFERENCES lookup_ticket_state(code);

ALTER TABLE ticket_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE ticket_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE asset_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE asset_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE action_plan_category_draft ADD site_id INTEGER REFERENCES lookup_site_id(code);
ALTER TABLE action_plan_category ADD site_id INTEGER REFERENCES lookup_site_id(code);

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

ALTER TABLE lookup_step_actions ADD constant_id INT;
