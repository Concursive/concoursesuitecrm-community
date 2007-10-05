-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

-- ticket table
ALTER TABLE ticket ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket ADD COLUMN expectation INTEGER;
ALTER TABLE ticket ADD COLUMN key_count INT;
ALTER TABLE ticket ADD COLUMN est_resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN assigned_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD COLUMN status_id INTEGER REFERENCES lookup_ticket_status(code);
ALTER TABLE ticket ADD COLUMN trashed_date TIMESTAMP NULL;
ALTER TABLE ticket ADD COLUMN user_group_id INTEGER REFERENCES user_group(group_id);
ALTER TABLE ticket ADD COLUMN cause_id INTEGER REFERENCES lookup_ticket_cause(code);
ALTER TABLE ticket ADD COLUMN resolution_id INTEGER REFERENCES lookup_ticket_resolution(code);
ALTER TABLE ticket ADD COLUMN defect_id INTEGER REFERENCES ticket_defect(defect_id);
ALTER TABLE ticket ADD COLUMN escalation_level INT REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticket ADD COLUMN resolvable TINYINT(1) NOT NULL DEFAULT '1';
ALTER TABLE ticket ADD COLUMN resolvedby INT REFERENCES `access`(user_id);
ALTER TABLE ticket ADD COLUMN resolvedby_department_code INT REFERENCES lookup_department(code);
ALTER TABLE ticket ADD COLUMN state_id INTEGER REFERENCES lookup_ticket_state(code);
ALTER TABLE ticket ADD COLUMN site_id INT REFERENCES lookup_site_id(code);
ALTER TABLE ticket ADD COLUMN submitter_id INT REFERENCES organization;
ALTER TABLE ticket ADD COLUMN submitter_contact_id INT REFERENCES contact;

-- ticketlog table
ALTER TABLE ticketlog ADD COLUMN escalation_code INT REFERENCES lookup_ticket_escalation(code);
ALTER TABLE ticketlog ADD COLUMN state_id INT REFERENCES lookup_ticket_state(code);
ALTER TABLE ticketlog ADD COLUMN messages_deleted BOOLEAN DEFAULT false;
ALTER TABLE ticketlog ADD COLUMN message_summary TEXT;
