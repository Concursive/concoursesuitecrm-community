ALTER TABLE import ADD COLUMN rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE import ADD COLUMN comments TEXT;

CREATE TABLE contact_message (
  id SERIAL PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES message(id),
  received_date TIMESTAMP(3) NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE access ADD COLUMN allow_webdav_access BOOLEAN;
ALTER TABLE access ADD COLUMN allow_httpapi_access BOOLEAN;

ALTER TABLE access ALTER COLUMN allow_webdav_access SET DEFAULT true;
ALTER TABLE access ALTER COLUMN allow_httpapi_access SET DEFAULT true;

UPDATE access SET allow_webdav_access = true;
UPDATE access SET allow_httpapi_access = true;

ALTER TABLE access ALTER COLUMN allow_webdav_access SET NOT NULL;
ALTER TABLE access ALTER COLUMN allow_httpapi_access SET NOT NULL;

CREATE TABLE opportunity_component_log(
  id serial NOT NULL,
  component_id INT REFERENCES opportunity_component(id),
  header_id INT REFERENCES opportunity_header(opp_id),
  description VARCHAR(80),
  closeprob FLOAT,
  closedate TIMESTAMP(3) NOT NULL,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  owner INT NOT NULL REFERENCES access(user_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  closedate_timezone VARCHAR(255)
);

ALTER TABLE opportunity_component_log ADD COLUMN closed TIMESTAMP(3);

ALTER TABLE action_step ADD COLUMN campaign_id INTEGER REFERENCES campaign(campaign_id);
ALTER TABLE action_step ADD COLUMN allow_duplicate_recipient boolean;
UPDATE action_step SET allow_duplicate_recipient = false;
ALTER TABLE action_step ALTER COLUMN allow_duplicate_recipient SET NOT NULL;
ALTER TABLE action_step ALTER COLUMN allow_duplicate_recipient SET DEFAULT false;

CREATE TABLE campaign_group_map (
  map_id SERIAL PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INT NOT NULL REFERENCES user_group(group_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

CREATE INDEX contact_org_id_idx ON contact(org_id) WHERE org_id IS NOT NULL AND org_id > 0;
CREATE INDEX contact_islead_idx ON contact(lead) WHERE lead = true;

CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);
CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);

ALTER TABLE contact_textmessageaddress ADD COLUMN textmessageaddress_type_temp INTEGER;

UPDATE contact_textmessageaddress SET textmessageaddress_type_temp = textmessageaddress_type;

ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type;

ALTER TABLE contact_textmessageaddress RENAME COLUMN  textmessageaddress_type_temp TO textmessageaddress_type;
ALTER TABLE contact_textmessageaddress ADD CONSTRAINT "$2" foreign key (textmessageaddress_type) REFERENCES lookup_textmessage_types(code);

UPDATE action_plan_work SET current_phase = NULL where current_phase = -1;
ALTER TABLE action_plan_work ADD CONSTRAINT "$6" FOREIGN KEY (current_phase) REFERENCES action_phase(phase_id);

UPDATE action_item_work SET status_id = null WHERE status_id = -1;
UPDATE action_phase_work SET status_id = null WHERE status_id = -1;

INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'customFieldDataList', 'org.aspcfs.modules.base.CustomFieldDataList');

UPDATE ticket SET user_group_id = NULL WHERE trashed_date IS NOT NULL;

UPDATE lookup_lists_lookup SET module_id = (SELECT category_id FROM permission_category WHERE constant = '1111031131'  ) WHERE category_id = '1111031131';
