ALTER TABLE import ADD rating INTEGER REFERENCES lookup_contact_rating(code);
ALTER TABLE import ADD comments TEXT;

CREATE TABLE contact_message (
  id INT IDENTITY PRIMARY KEY,
  message_id INTEGER NOT NULL REFERENCES message(id),
  received_date DATETIME NOT NULL,
  received_from INT NOT NULL REFERENCES contact(contact_id),
  received_by INT NOT NULL REFERENCES access(user_id)
);

ALTER TABLE access ADD allow_webdav_access BIT DEFAULT 1;
ALTER TABLE access ADD allow_httpapi_access BIT DEFAULT 1;

UPDATE access SET allow_webdav_access = 1;
UPDATE access SET allow_httpapi_access = 1;

ALTER TABLE access ALTER COLUMN allow_webdav_access BIT NOT NULL;
ALTER TABLE access ALTER COLUMN allow_httpapi_access BIT NOT NULL;

CREATE TABLE opportunity_component_log(
  id INT IDENTITY PRIMARY KEY,
  component_id INT REFERENCES opportunity_component(id),
  header_id INT REFERENCES opportunity_header(opp_id),
  description VARCHAR(80),
  closeprob FLOAT,
  closedate DATETIME NOT NULL,
  terms FLOAT,
  units CHAR(1),
  lowvalue FLOAT,
  guessvalue FLOAT,
  highvalue FLOAT,
  stage INT REFERENCES lookup_stage(code),
  owner INT NOT NULL REFERENCES access(user_id),
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  closedate_timezone VARCHAR(255)
);

ALTER TABLE opportunity_component_log ADD closed DATETIME;

ALTER TABLE action_step ADD campaign_id INT REFERENCES campaign(campaign_id);
ALTER TABLE action_step ADD allow_duplicate_recipient BIT DEFAULT 0 NOT NULL;
UPDATE action_step SET allow_duplicate_recipient = 0;

CREATE TABLE campaign_group_map (
  map_id INT IDENTITY PRIMARY KEY,
  campaign_id INT NOT NULL REFERENCES campaign(campaign_id),
  user_group_id INT NOT NULL REFERENCES user_group(group_id)
);

CREATE INDEX ticketlink_project_idx ON ticketlink_project(ticket_id);

CREATE INDEX contact_org_id_idx ON contact(org_id);
CREATE INDEX contact_islead_idx ON contact(lead);

CREATE INDEX contact_address_prim_idx ON contact_address(primary_address);

CREATE INDEX contact_email_prim_idx ON contact_emailaddress(primary_email);

CREATE INDEX contact_lead_skip_u_idx ON contact_lead_skipped_map(user_id);
CREATE INDEX contact_lead_read_u_idx ON contact_lead_read_map(user_id);
CREATE INDEX contact_lead_read_c_idx ON contact_lead_read_map(contact_id);

ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type_temp INTEGER;
UPDATE contact_textmessageaddress SET textmessageaddress_type_temp = textmessageaddress_type;

EXEC sp_executesql N'
DECLARE @sql nvarchar(4000)
DECLARE CNSTR CURSOR LOCAL FAST_FORWARD READ_ONLY FOR
     SELECT ''ALTER TABLE ''+@table+'' DROP CONSTRAINT ''+QUOTENAME(o.name)
     FROM dbo.syscolumns
     INNER JOIN dbo.sysobjects ON syscolumns.id = sysobjects.id
     INNER JOIN dbo.sysreferences ON syscolumns.id = sysreferences.fkeyid
     INNER JOIN dbo.sysobjects o ON sysreferences.constid = o.id
     WHERE sysobjects.name = ''contact_textmessageaddress''
       AND syscolumns.name = ''textmessageaddress_type''
       AND o.name LIKE ''FK__contact_t__textm__%''
OPEN CNSTR
FETCH CNSTR INTO @sql
WHILE @@FETCH_STATUS=0
BEGIN
    EXEC sp_executesql @sql
    FETCH CNSTR INTO @sql
END
CLOSE CNSTR DEALLOCATE CNSTR',N'@table nvarchar(261)','[contact_textmessageaddress]'
GO

ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type;

ALTER TABLE contact_textmessageaddress ADD textmessageaddress_type INTEGER REFERENCES lookup_textmessage_types(code);
UPDATE contact_textmessageaddress SET textmessageaddress_type = textmessageaddress_type_temp;
ALTER TABLE contact_textmessageaddress DROP COLUMN textmessageaddress_type_temp;

UPDATE action_plan_work SET current_phase = NULL where current_phase = -1;
ALTER TABLE action_plan_work ADD CONSTRAINT "fk_current_phase" FOREIGN KEY (current_phase) REFERENCES action_phase(phase_id);

UPDATE action_item_work SET status_id = null WHERE status_id = -1;
UPDATE action_phase_work SET status_id = null WHERE status_id = -1;

INSERT INTO sync_table (system_id, element_name, mapped_class_name) VALUES (4, 'customFieldDataList', 'org.aspcfs.modules.base.CustomFieldDataList');

UPDATE ticket SET user_group_id = NULL WHERE trashed_date IS NOT NULL;

UPDATE lookup_lists_lookup SET module_id = (SELECT category_id FROM permission_category WHERE constant = '1111031131'  ) WHERE category_id = '1111031131';
