--Knowledge Base Status Lookup Table

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

-- Knowledge Base table
CREATE TABLE knowledge_base (
  kb_id SERIAL PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  item_id INTEGER REFERENCES project_files(item_id),
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modifiedby INT NOT NULL REFERENCES access(user_id),
	status INTEGER NOT NULL DEFAULT 1 REFERENCES lookup_kb_status(code),
	portal_access_allowed BOOLEAN  NOT NULL DEFAULT false
);
