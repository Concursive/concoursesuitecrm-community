--
--  PostgreSQL Table additions to the ticket table that must be done last
--
--@author     mrajkowski
--@created    April 30, 2004
--@version    $Id$

ALTER TABLE ticket ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket ADD COLUMN expectation INTEGER;
ALTER TABLE ticket ADD COLUMN key_count INT;
ALTER TABLE ticket ADD COLUMN est_resolution_date_timezone VARCHAR(255);
--ALTER TABLE ticket ADD COLUMN status_id INTEGER REFERENCES lookup_ticket_status(code);

