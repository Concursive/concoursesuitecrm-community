/**
 *  MSSQL Table Creation
 *
 *@author     matt rajkowski
 *@created    October 2, 2002
 *@version    $Id$
 */

CREATE TABLE task (
  task_id INT IDENTITY PRIMARY KEY,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  priority INT NOT NULL,
  description VARCHAR(80),
  duedate DATETIME,
  reminderid INT,
  notes VARCHAR(255),
  sharing INT NOT NULL,
  complete BIT DEFAULT 0 NOT NULL,
  enabled BIT DEFAULT 0 NOT NULL,
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT REFERENCES access(user_id),
  estimatedloe INTEGER DEFAULT -1,
  owner INTEGER NOT NULL,
  completedate DATETIME
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

