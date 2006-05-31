-- Knowledge Base table
CREATE SEQUENCE knowledge_base_kb_id_seq;
CREATE TABLE knowledge_base (
  kb_id INT PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description CLOB,
  item_id INTEGER REFERENCES project_files(item_id),
	entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  enteredby INT NOT NULL REFERENCES access(user_id),
	modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	modifiedby INT NOT NULL REFERENCES access(user_id)
);
