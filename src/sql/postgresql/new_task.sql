/**
 *  PostgreSQL Table Creation
 *
 *@author     a mathur
 *@created    September 2, 2002
 *@version    $Id$
 */
 
CREATE TABLE task (
  task_id SERIAL PRIMARY KEY,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  priority INT NOT NULL,
  description VARCHAR(80),
  duedate DATE,
  reminderid INT,
  notes VARCHAR(255),
  sharing INT NOT NULL,
  complete BOOLEAN DEFAULT false NOT NULL,
  enabled BOOLEAN DEFAULT false NOT NULL,
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT REFERENCES access(user_id),
  estimatedloe INTEGER DEFAULT -1 ,
  owner INTEGER NOT NULL,
  completedate TIMESTAMP(3)
  );

CREATE TABLE tasklink_contact (
  task_id INT NOT NULL REFERENCES task,
  contact_id INT NOT NULL REFERENCES contact(contact_id),
  notes VARCHAR(255)
  );
CREATE TABLE tasklink_ticket (
  task_id INT NOT NULL REFERENCES task,
  ticket_id INT NOT NULL  REFERENCES ticket(ticketid)
  );

