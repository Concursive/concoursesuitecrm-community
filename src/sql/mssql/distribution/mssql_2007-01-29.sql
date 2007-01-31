CREATE TABLE message_file_attachment (
  attachment_id INT IDENTITY PRIMARY KEY,
  link_module_id INT NOT NULL,
  link_item_id INT NOT NULL,
  file_item_id INT REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  size INT DEFAULT 0,
  version FLOAT DEFAULT 0
);

CREATE INDEX "message_f_link_module_id" ON "message_file_attachment" (link_module_id);
CREATE INDEX "message_f_link_item_id" ON "message_file_attachment" (link_item_id);

CREATE TABLE lookup_report_type (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BIT DEFAULT 0,
  level INTEGER DEFAULT 0,
  enabled BIT DEFAULT 1,
  constant INTEGER NOT NULL DEFAULT 1,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE report_queue ADD output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD email BIT DEFAULT 0;

CREATE INDEX org_owner_idx ON organization(owner);
CREATE INDEX org_enteredby_idx ON organization(enteredby);
CREATE INDEX org_modifiedby_idx ON organization(modifiedby);
CREATE INDEX org_industry_temp_code_idx ON organization(industry_temp_code);
CREATE INDEX org_account_size_idx ON organization(account_size);
CREATE INDEX org_org_id_miner_only_idx ON organization(org_id, miner_only);
CREATE INDEX org_site_id_idx ON organization(site_id);
CREATE INDEX org_status_id_idx ON organization(status_id);
CREATE INDEX org_addr_pri_address_idx ON organization_address(primary_address);
CREATE INDEX org_addr_org_id_idx ON organization_address(org_id);
