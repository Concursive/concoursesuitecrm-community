--
--  PostgreSQL Table additions to the ticket table that must be done last
--
--@author     mrajkowski
--@created    April 30, 2004
--@version    $Id$

ALTER TABLE ticket
ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);

ALTER TABLE ticketlog
ADD COLUMN customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
