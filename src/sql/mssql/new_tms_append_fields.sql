--
--  MSSQL Table additions to the ticket table that must be done last
--
--@author     mrajkowski
--@created    April 30, 2004
--@version    $Id$
 
ALTER TABLE ticket
ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);

ALTER TABLE ticketlog
ADD customer_product_id INTEGER REFERENCES customer_product(customer_product_id);
