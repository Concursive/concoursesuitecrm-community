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
	modifiedby INT NOT NULL REFERENCES access(user_id)
);
