/* Field to track reports in permission_category */
ALTER TABLE permission_category ADD COLUMN reports BOOLEAN;
ALTER TABLE permission_category ALTER COLUMN reports SET DEFAULT false;
UPDATE permission_category SET reports = false;

/* Reports */
CREATE TABLE report (
  report_id SERIAL PRIMARY KEY,
  category_id INT NOT NULL REFERENCES permission_category(category_id),
  permission_id INT NULL REFERENCES permission(permission_id),
  filename VARCHAR(300) NOT NULL,
  type INTEGER NOT NULL DEFAULT 1,
  title VARCHAR(300) NOT NULL,
  description VARCHAR(1024) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT true,
  custom BOOLEAN DEFAULT false
);

CREATE TABLE report_criteria (
  criteria_id SERIAL PRIMARY KEY,
  report_id INT NOT NULL REFERENCES report(report_id),
  owner INT NOT NULL REFERENCES access(user_id),
  subject VARCHAR(512) NOT NULL,
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  modified TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modifiedby INT NOT NULL REFERENCES access(user_id),
  enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE report_criteria_parameter (
  parameter_id SERIAL PRIMARY KEY,
  criteria_id INTEGER NOT NULL REFERENCES report_criteria(criteria_id),
  parameter VARCHAR(255) NOT NULL,
  value TEXT
);

CREATE TABLE report_queue (
  queue_id SERIAL PRIMARY KEY,
  report_id INTEGER NOT NULL REFERENCES report(report_id),
  entered TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES access(user_id),
  processed TIMESTAMP(3) NULL DEFAULT NULL,
  status INT NOT NULL DEFAULT 0,
  filename VARCHAR(256),
  filesize INT DEFAULT -1,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE report_queue_criteria (
  criteria_id SERIAL PRIMARY KEY,
  queue_id INTEGER NOT NULL REFERENCES report_queue(queue_id),
  parameter VARCHAR(255) NOT NULL,
  value TEXT
);
