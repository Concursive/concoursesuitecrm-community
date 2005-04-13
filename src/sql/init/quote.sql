
-- lookup_quote_status
INSERT INTO lookup_quote_status (description, level) VALUES ('Incomplete', 10);
INSERT INTO lookup_quote_status (description, level) VALUES ('Pending internal approval', 20);
INSERT INTO lookup_quote_status (description, level) VALUES ('Approved internally', 30);
INSERT INTO lookup_quote_status (description, level) VALUES ('Unapproved internally', 40);
INSERT INTO lookup_quote_status (description, level) VALUES ('Pending customer acceptance', 50);
INSERT INTO lookup_quote_status (description, level) VALUES ('Accepted by customer', 60);
INSERT INTO lookup_quote_status (description, level) VALUES ('Rejected by customer', 70);
INSERT INTO lookup_quote_status (description, level) VALUES ('Changes requested by customer', 80);
INSERT INTO lookup_quote_status (description, level) VALUES ('Cancelled', 90);
INSERT INTO lookup_quote_status (description, level) VALUES ('Complete', 100);

-- lookup_quote_type
INSERT INTO lookup_quote_type ( description ) VALUES ('New');
INSERT INTO lookup_quote_type ( description ) VALUES ('Change');
INSERT INTO lookup_quote_type ( description ) VALUES ('Upgrade/Downgrade');
INSERT INTO lookup_quote_type ( description ) VALUES ('Disconnect');
INSERT INTO lookup_quote_type ( description ) VALUES ('Move');

-- lookup_quote_source
INSERT INTO lookup_quote_source ( description ) VALUES ('Online');
INSERT INTO lookup_quote_source ( description ) VALUES ('Email');
INSERT INTO lookup_quote_source ( description ) VALUES ('Incoming Phone Call');
INSERT INTO lookup_quote_source ( description ) VALUES ('Outgoing Phone Call');
INSERT INTO lookup_quote_source ( description ) VALUES ('Mail');
INSERT INTO lookup_quote_source ( description ) VALUES ('Agent Request');
INSERT INTO lookup_quote_source (description) VALUES ('In Person');

-- lookup_quote_delivery
INSERT INTO lookup_quote_delivery (description) VALUES ('Email');
INSERT INTO lookup_quote_delivery (description) VALUES ('Fax');
INSERT INTO lookup_quote_delivery (description) VALUES ('USPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('FedEx');
INSERT INTO lookup_quote_delivery (description) VALUES ('UPS');
INSERT INTO lookup_quote_delivery (description) VALUES ('In Person');


