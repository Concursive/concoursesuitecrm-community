/* New tables for calls (activities) */
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
  next_call_type_id INT NULL,
  canceled_type BOOLEAN NOT NULL DEFAULT false
);

ALTER TABLE lookup_call_result ADD FOREIGN KEY(next_call_type_id) REFERENCES call_log(call_id);

/* Changes for converting Calls into Activities, preserving any data */
ALTER TABLE call_log DROP COLUMN followup_notes;

DROP INDEX call_log_cidx;
ALTER TABLE call_log ADD COLUMN alertdate_temp TIMESTAMP(3);
UPDATE call_log SET alertdate_temp = alertdate;
ALTER TABLE call_log DROP COLUMN alertdate;
ALTER TABLE call_log ADD COLUMN alertdate TIMESTAMP(3);
UPDATE call_log SET alertdate = alertdate_temp;
ALTER TABLE call_log DROP COLUMN alertdate_temp;
CREATE INDEX "call_log_cidx" ON "call_log" USING btree ("alertdate", "enteredby");

ALTER TABLE call_log DROP COLUMN followup_date;
ALTER TABLE call_log ADD COLUMN followup_date TIMESTAMP(3);

ALTER TABLE call_log ADD COLUMN parent_id INT NULL REFERENCES call_log(call_id);
ALTER TABLE call_log ADD COLUMN owner INT NULL REFERENCES access(user_id);
ALTER TABLE call_log ADD COLUMN assignedby INT REFERENCES access(user_id);

ALTER TABLE call_log ADD COLUMN assign_date TIMESTAMP(3);
ALTER TABLE call_log ALTER assign_date SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE call_log ADD COLUMN completedby INT REFERENCES access(user_id);
ALTER TABLE call_log ADD COLUMN complete_date TIMESTAMP(3) NULL;
ALTER TABLE call_log ADD COLUMN result_id INT REFERENCES lookup_call_result(result_id);
ALTER TABLE call_log ADD COLUMN priority_id INT REFERENCES lookup_call_priority(code);

ALTER TABLE call_log ADD COLUMN status_id INT;
UPDATE call_log SET status_id = 1;
ALTER TABLE call_log ADD CONSTRAINT call_status_not_null CHECK(status_id IS NOT NULL);
ALTER TABLE call_log ALTER status_id SET DEFAULT 1;

ALTER TABLE call_log ADD COLUMN reminder_value INT NULL;
ALTER TABLE call_log ADD COLUMN reminder_type_id INT NULL REFERENCES lookup_call_reminder(code);


