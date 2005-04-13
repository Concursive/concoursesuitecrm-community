--
--  MSSQL Table additions to the ticket table that must be done last
--
--@author     mrajkowski
--@created    April 30, 2004
--@version    $Id$
 
ALTER TABLE ticket ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
ALTER TABLE ticket ADD expectation INTEGER;
ALTER TABLE ticket ADD key_count INT;
ALTER TABLE ticket ADD est_resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD assigned_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD resolution_date_timezone VARCHAR(255);
ALTER TABLE ticket ADD status_id INTEGER REFERENCES lookup_ticket_status(code);

