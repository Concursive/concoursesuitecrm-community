CREATE SEQUENCE lookup_task_priority_code_seq;
CREATE TABLE lookup_task_priority (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_task_loe_code_seq;
CREATE TABLE lookup_task_loe (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);

CREATE SEQUENCE lookup_task_category_code_seq;
CREATE TABLE lookup_task_category (
  code INT PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" INTEGER DEFAULT 0,
  enabled boolean DEFAULT true
);


CREATE SEQUENCE task_task_id_seq;
CREATE TABLE task (
  task_id INT  PRIMARY KEY,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ,
  enteredby INT NOT NULL REFERENCES access(user_id),
  priority INTEGER NOT NULL REFERENCES lookup_task_priority,
  description VARCHAR(80),
  duedate TIMESTAMP,
  reminderid INT,
  notes CLOB,
  sharing INT NOT NULL,
  complete boolean DEFAULT false NOT NULL,
  enabled boolean DEFAULT false NOT NULL,
  modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP  NOT NULL ,
  modifiedby INT REFERENCES access(user_id),
  estimatedloe FLOAT,
  estimatedloetype INTEGER REFERENCES lookup_task_loe,
  type INTEGER DEFAULT 1,
  owner INTEGER REFERENCES access(user_id),
  completedate TIMESTAMP,
  category_id INTEGER REFERENCES lookup_task_category,
  duedate_timezone VARCHAR(255),
  trashed_date TIMESTAMP
);


CREATE TABLE tasklink_contact (
  task_id INT NOT NULL REFERENCES task,
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  notes CLOB
);

CREATE TABLE tasklink_ticket (
  task_id INT NOT NULL REFERENCES task,
  ticket_id INT NOT NULL REFERENCES ticket(ticketid)
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
