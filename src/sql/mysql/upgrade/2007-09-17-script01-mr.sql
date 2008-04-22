CREATE TABLE lookup_kb_status (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id INTEGER NOT NULL,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);
