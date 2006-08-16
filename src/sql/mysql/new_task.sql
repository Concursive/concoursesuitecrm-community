-- ----------------------------------------------------------------------------
--  PostgreSQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------
 
CREATE TABLE lookup_task_priority (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_task_loe (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_task_category (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_ticket_task_category (
  code INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  description VARCHAR(255) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE task (
  task_id INT AUTO_INCREMENT PRIMARY KEY,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  priority INTEGER NOT NULL REFERENCES lookup_task_priority,
  description VARCHAR(255),
  duedate TIMESTAMP NULL,
  reminderid INT,
  notes TEXT,
  sharing INT NOT NULL,
  complete BOOLEAN DEFAULT false NOT NULL,
  enabled BOOLEAN DEFAULT false NOT NULL,
  modified TIMESTAMP NULL,
  modifiedby INT REFERENCES `access`(user_id),
  estimatedloe FLOAT,
  estimatedloetype INTEGER REFERENCES lookup_task_loe,
  type INTEGER DEFAULT 1,
  owner INTEGER REFERENCES `access`(user_id),
  completedate TIMESTAMP NULL,
  category_id INTEGER REFERENCES lookup_task_category,
  duedate_timezone VARCHAR(255),
  trashed_date TIMESTAMP NULL,
  ticket_task_category_id INTEGER REFERENCES lookup_ticket_task_category(code)
);

CREATE TABLE tasklink_contact (
  task_id INT NOT NULL REFERENCES task,
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  notes TEXT
);

CREATE TABLE tasklink_ticket (
  task_id INT NOT NULL REFERENCES task,
  ticket_id INT NOT NULL REFERENCES ticket(ticketid),
  category_id INT REFERENCES lookup_ticket_task_category(code)
);

CREATE TABLE tasklink_project (
  task_id INT NOT NULL REFERENCES task,
  project_id INT NOT NULL REFERENCES projects(project_id)
);

CREATE TABLE taskcategory_project (
  category_id INTEGER NOT NULL REFERENCES lookup_task_category,
  project_id INTEGER NOT NULL REFERENCES projects(project_id)
);

CREATE TABLE taskcategorylink_news (
  news_id INTEGER NOT NULL REFERENCES project_news(news_id),
  category_id INTEGER NOT NULL REFERENCES lookup_task_category
);
