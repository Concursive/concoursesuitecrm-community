 /**
 *  Upgrade script for integrating the following:
 *  - Activities
 *  - Primary tagging for Contact Address and Phone  
 *  - Indexes for improving performance
 *
 *@author     Akhilesh Mathur
 *@created    July 22, 2004
 */
 
/* Activity Upgrade */
CREATE TABLE lookup_call_priority (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  weight INT NOT NULL
);

CREATE TABLE lookup_call_reminder (
  code INT IDENTITY PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BIT DEFAULT 0,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1
);

CREATE TABLE lookup_call_result (
  result_id INT IDENTITY PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  level INT DEFAULT 0,
  enabled BIT DEFAULT 1,
  next_required BIT NOT NULL DEFAULT 0,
  next_days INT NOT NULL DEFAULT 0,
  next_call_type_id INT NULL REFERENCES call_log(call_id),
  canceled_type BIT NOT NULL DEFAULT 0
);

/* Load initial data into lookups */

insert into lookup_call_priority(description, level, weight) values ('Low', 10, 1);
insert into lookup_call_priority(description, level, weight) values ('Medium', 20, 2);
insert into lookup_call_priority(description, level, weight) values ('High', 30, 3);

insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (1, 'Minute(s)', 1, 60);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (2, 'Hour(s)', 0, 3600);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (3, 'Day(s)', 0, 86400);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (4, 'Week(s)', 0, 604800);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (5, 'Month(s)', 0, 18144000);

INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (1, 'Yes - Business progressing', 10, 1, 1, 0, NULL, 0);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (2, 'No - No business at this time', 20, 1, 0, 0, NULL, 0);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (3, 'Unsure - Unsure or no contact made', 30, 1, 1, 0, NULL, 0);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (4, 'Lost to competitor', 140, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (5, 'No further interest', 150, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (6, 'Event postponed/canceled', 160, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (7, 'Another pending action', 170, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (8, 'Another contact handling event', 180, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (9, 'Contact no longer with company', 190, 1, 0, 0, NULL, 1);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (10, 'Servicing', 120, 1, 0, 0, NULL, 0);

ALTER TABLE call_log ADD alert_tmp VARCHAR(255);
UPDATE call_log SET alert_tmp = alert;
ALTER TABLE call_log DROP COLUMN alert;
ALTER TABLE call_log ADD alert VARCHAR(255);
UPDATE call_log SET alert = alert_tmp;
ALTER TABLE call_log DROP COLUMN alert_tmp;

ALTER TABLE call_log ADD alert_call_type_id INT REFERENCES lookup_call_types(code);
ALTER TABLE call_log ADD parent_id INT NULL REFERENCES call_log(call_id);
ALTER TABLE call_log ADD owner INT NULL REFERENCES access(user_id);
ALTER TABLE call_log ADD assignedby INT REFERENCES access(user_id);
ALTER TABLE call_log ADD assign_date DATETIME;
ALTER TABLE call_log ALTER assign_date SET DEFAULT CURRENT_TIMESTAMP;
UPDATE call_log SET assign_date = CURRENT_TIMESTAMP;
ALTER TABLE call_log ADD completedby INT REFERENCES access(user_id);
ALTER TABLE call_log ADD complete_date DATETIME NULL;
ALTER TABLE call_log ADD result_id INT REFERENCES lookup_call_result(result_id);
ALTER TABLE call_log ADD priority_id INT REFERENCES lookup_call_priority(code);
ALTER TABLE call_log ADD status_id INT;
UPDATE call_log SET status_id = 1;
ALTER TABLE call_log ADD CONSTRAINT call_status_not_null_CHECK(status_id NOT NULL);
ALTER TABLE call_log ALTER status_id SET DEFAULT 1;
ALTER TABLE call_log ADD reminder_value INT NULL;
ALTER TABLE call_log ADD reminder_type_id INT NULL REFERENCES lookup_call_reminder(code);

/* Activity Indexes */
CREATE INDEX "call_log_entered_idx" ON "call_log" (entered);
CREATE INDEX "call_org_id_idx" ON "call_log" (org_id);
CREATE INDEX "call_contact_id_idx" ON "call_log" (contact_id);
CREATE INDEX "call_opp_id_idx" ON "call_log" (opp_id);

/* Primary phone and address */
ALTER TABLE contact_phone ADD primary_number BIT DEFAULT 0;
UPDATE contact_phone set primary_number = 0;

ALTER table contact_address ADD COLUMN primary_address BIT default 0;
update contact_address set primary_address = 0;

/* Ticket Performance */
CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);

