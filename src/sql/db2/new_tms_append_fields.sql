
ALTER TABLE ticket
 ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket
 ADD COLUMN expectation INTEGER;
ALTER TABLE ticket
 ADD COLUMN key_count INTEGER;
ALTER TABLE ticket
 ADD COLUMN est_resolution_date_timezone VARGRAPHIC(255);
ALTER TABLE ticket
 ADD COLUMN assigned_date_timezone VARGRAPHIC(255);
ALTER TABLE ticket
 ADD COLUMN resolution_date_timezone VARGRAPHIC(255);
ALTER TABLE ticket
 ADD COLUMN status_id INTEGER REFERENCES lookup_ticket_status(code);
ALTER TABLE ticket
 ADD COLUMN trashed_date TIMESTAMP;
ALTER TABLE ticket
 ADD COLUMN user_group_id INTEGER REFERENCES user_group(group_id);
ALTER TABLE ticket
 ADD COLUMN cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket
 ADD COLUMN resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);
ALTER TABLE ticket
 ADD COLUMN defect_id INTEGER REFERENCES ticket_defect(defect_id);
ALTER TABLE ticket
 ADD COLUMN escalation_level INTEGER REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticket
 ADD COLUMN resolvable CHAR(1) DEFAULT '1' NOT NULL;
ALTER TABLE ticket
 ADD COLUMN resolvedby INTEGER REFERENCES "access"(user_id);
ALTER TABLE ticket
 ADD COLUMN resolvedby_department_code INTEGER REFERENCES lookup_department(code);
ALTER TABLE ticket
 ADD COLUMN state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticket
 ADD COLUMN site_id INTEGER REFERENCES lookup_site_id(code);

ALTER TABLE ticketlog
 ADD COLUMN escalation_code INTEGER REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticketlog
 ADD COLUMN state_id INTEGER REFERENCES lookup_ticket_state(code);

