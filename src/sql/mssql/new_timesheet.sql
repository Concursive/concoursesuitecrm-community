-- MSSQL

CREATE TABLE timesheet (
  timesheet_id INT IDENTITY PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(user_id),
  entry_date DATETIME NOT NULL,
  hours FLOAT NOT NULL DEFAULT 0,
  start_time DATETIME NULL,
  end_time DATETIME NULL,
  verified BIT NOT NULL DEFAULT 0,
  approved BIT NOT NULL DEFAULT 0,
  approved_by INTEGER REFERENCES users(user_id),
  available BIT NOT NULL DEFAULT 1,
  unavailable BIT NOT NULL DEFAULT 0,
  vacation BIT NOT NULL DEFAULT 0,
  vacation_approved BIT NOT NULL DEFAULT 0,
  entered DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredBy INTEGER NOT NULL REFERENCES users(user_id),
  modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedBy INTEGER NOT NULL REFERENCES users(user_id)
);

CREATE TABLE timesheet_projects (
  id INT IDENTITY PRIMARY KEY,
  timesheet_id INTEGER NOT NULL REFERENCES timesheet(timesheet_id),
  project_id INTEGER REFERENCES projects(project_id),
  hours FLOAT NOT NULL DEFAULT 0,
  start_time DATETIME NULL,
  end_time DATETIME NULL
);


