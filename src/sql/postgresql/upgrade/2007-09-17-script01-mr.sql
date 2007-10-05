
CREATE TABLE lookup_kb_status (
  code SERIAL PRIMARY KEY,
  description VARCHAR(300) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  constant_id INTEGER NOT NULL,
  entered timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
