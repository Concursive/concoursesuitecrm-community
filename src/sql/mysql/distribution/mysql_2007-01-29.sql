CREATE TABLE message_file_attachment (
  attachment_id INT AUTO_INCREMENT PRIMARY KEY,
  link_module_id INT NOT NULL,
  link_item_id INT NOT NULL,
  file_item_id INT REFERENCES project_files(item_id),
  filename VARCHAR(255) NOT NULL,
  size INT DEFAULT 0,
  version FLOAT DEFAULT 0
);

CREATE INDEX message_f_link_module_id ON message_file_attachment (link_module_id);
CREATE INDEX message_f_link_item_id ON message_file_attachment (link_item_id);

CREATE TABLE lookup_report_type (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant INTEGER DEFAULT 1 NOT NULL,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);
ALTER TABLE report_queue ADD COLUMN output_type INT REFERENCES lookup_report_type(code);
ALTER TABLE report_queue ADD COLUMN email BOOLEAN DEFAULT false;

UPDATE permission SET active = true, enabled = true WHERE permission = 'product-catalog';
