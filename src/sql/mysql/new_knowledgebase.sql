-- ----------------------------------------------------------------------------
--  PostgreSQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- Knowledge Base table
CREATE TABLE knowledge_base (
  kb_id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INTEGER REFERENCES ticket_category(id),
  title VARCHAR(255) NOT NULL,
  description TEXT,
  item_id INTEGER REFERENCES project_files(item_id),
	-- record status
	entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
	modified TIMESTAMP NULL,
	modifiedby INT NOT NULL REFERENCES `access`(user_id)
);
