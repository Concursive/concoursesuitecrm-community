-- ----------------------------------------------------------------------------
--  PostgreSQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE timesheet (
  timesheet_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(user_id),
  entry_date TIMESTAMP NULL,
  hours FLOAT NOT NULL DEFAULT 0,
  start_time TIMESTAMP NULL,
  end_time TIMESTAMP NULL,
  verified BOOLEAN NOT NULL DEFAULT false,
  approved BOOLEAN NOT NULL DEFAULT false,
  approved_by INTEGER REFERENCES users(user_id),
  available BOOLEAN NOT NULL DEFAULT true,
  unavailable BOOLEAN NOT NULL DEFAULT false,
  vacation BOOLEAN NOT NULL DEFAULT false,
  vacation_approved BOOLEAN NOT NULL DEFAULT false,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES users(user_id),
  modified TIMESTAMP NULL,
  modifiedBy INTEGER NOT NULL REFERENCES users(user_id)
);

CREATE TABLE timesheet_projects (
  id INT AUTO_INCREMENT PRIMARY KEY,
  timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
  project_id INTEGER REFERENCES projects(project_id),
  hours FLOAT NOT NULL DEFAULT 0,
  start_time TIMESTAMP NULL,
  end_time TIMESTAMP NULL
);


