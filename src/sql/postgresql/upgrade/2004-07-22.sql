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
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  weight INTEGER NOT NULL
);

CREATE TABLE lookup_call_reminder (
  code SERIAL PRIMARY KEY,
  description VARCHAR(50) NOT NULL,
  base_value INTEGER DEFAULT 0 NOT NULL,
  default_item BOOLEAN DEFAULT false,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true
);

CREATE TABLE lookup_call_result (
  result_id SERIAL PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  level INTEGER DEFAULT 0,
  enabled BOOLEAN DEFAULT true,
  next_required BOOLEAN NOT NULL DEFAULT false,
  next_days INT NOT NULL DEFAULT 0,
  next_call_type_id INT NULL REFERENCES call_log(call_id),
  canceled_type BOOLEAN NOT NULL DEFAULT false
);

/* Load initial data into lookups */

insert into lookup_call_priority(description, level, weight) values ('Low', 10, 1);
insert into lookup_call_priority(description, level, weight) values ('Medium', 20, 2);
insert into lookup_call_priority(description, level, weight) values ('High', 30, 3);

insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (1, 'Minute(s)', 't', 60);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (2, 'Hour(s)', 'f', 3600);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (3, 'Day(s)', 'f', 86400);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (4, 'Week(s)', 'f', 604800);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (5, 'Month(s)', 'f', 18144000);

INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (1, 'Yes - Business progressing', 10, 't', 't', 0, NULL, 'f');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (2, 'No - No business at this time', 20, 't', 'f', 0, NULL, 'f');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (3, 'Unsure - Unsure or no contact made', 30, 't', 't', 0, NULL, 'f');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (4, 'Lost to competitor', 140, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (5, 'No further interest', 150, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (6, 'Event postponed/canceled', 160, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (7, 'Another pending action', 170, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (8, 'Another contact handling event', 180, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (9, 'Contact no longer with company', 190, 't', 'f', 0, NULL, 't');
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (10, 'Servicing', 120, 't', 'f', 0, NULL, 'f');

ALTER TABLE call_log ADD COLUMN alert_tmp VARCHAR(255);
UPDATE call_log SET alert_tmp = alert;
ALTER TABLE call_log DROP COLUMN alert;
ALTER TABLE call_log ADD COLUMN alert VARCHAR(255);
UPDATE call_log SET alert = alert_tmp;
ALTER TABLE call_log DROP COLUMN alert_tmp;

ALTER TABLE call_log ADD COLUMN alert_call_type_id INT REFERENCES lookup_call_types(code);
ALTER TABLE call_log ADD COLUMN parent_id INT NULL REFERENCES call_log(call_id);
ALTER TABLE call_log ADD COLUMN owner INT NULL REFERENCES access(user_id);
ALTER TABLE call_log ADD COLUMN assignedby INT REFERENCES access(user_id);
ALTER TABLE call_log ADD COLUMN assign_date TIMESTAMP(3);
ALTER TABLE call_log ALTER assign_date SET DEFAULT CURRENT_TIMESTAMP;
UPDATE call_log SET assign_date = CURRENT_TIMESTAMP;
ALTER TABLE call_log ADD COLUMN completedby INT REFERENCES access(user_id);
ALTER TABLE call_log ADD COLUMN complete_date TIMESTAMP(3) NULL;
ALTER TABLE call_log ADD COLUMN result_id INT REFERENCES lookup_call_result(result_id);
ALTER TABLE call_log ADD COLUMN priority_id INT REFERENCES lookup_call_priority(code);
ALTER TABLE call_log ADD COLUMN status_id INT;
ALTER TABLE call_log ADD CONSTRAINT call_status_not_null CHECK(status_id IS NOT NULL);
ALTER TABLE call_log ALTER status_id SET DEFAULT 1;
ALTER TABLE call_log ADD COLUMN reminder_value INT NULL;
ALTER TABLE call_log ADD COLUMN reminder_type_id INT NULL REFERENCES lookup_call_reminder(code);
UPDATE call_log SET result_id = 1;
UPDATE call_log SET status_id = 2;

/* Activity Indexes */
CREATE INDEX "call_log_entered_idx" ON "call_log" (entered);
CREATE INDEX "call_org_id_idx" ON "call_log" (org_id);
CREATE INDEX "call_contact_id_idx" ON "call_log" (contact_id);
CREATE INDEX "call_opp_id_idx" ON "call_log" (opp_id);

/* Primary phone and address */
ALTER table contact_phone ADD COLUMN primary_number boolean;
ALTER table contact_phone ALTER primary_number set default false;
update contact_phone set primary_number = false;
ALTER TABLE contact_phone ADD CONSTRAINT contact_phone_primnum_not_null CHECK(primary_number IS NOT NULL);

ALTER table contact_address ADD COLUMN primary_address boolean;
ALTER table contact_address ALTER primary_address set default false;
update contact_address set primary_address = false;
ALTER TABLE contact_address ADD CONSTRAINT contact_address_primaddr_not_null CHECK(primary_address IS NOT NULL);

/* Ticket Performance */
CREATE INDEX "ticket_problem_idx" ON "ticket" (problem);
CREATE INDEX "ticket_comment_idx" ON "ticket" (comment);
CREATE INDEX "ticket_solution_idx" ON "ticket" (solution);

