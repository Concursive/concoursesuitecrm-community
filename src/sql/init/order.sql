/* Lookup values for the order_entry lookup tables */

INSERT INTO lookup_order_status (description) VALUES ('Pending');
INSERT INTO lookup_order_status (description) VALUES ('In Progress');
INSERT INTO lookup_order_status (description) VALUES ('Cancelled');
INSERT INTO lookup_order_status (description) VALUES ('Rejected');
INSERT INTO lookup_order_status (description) VALUES ('Complete');
INSERT INTO lookup_order_status (description) VALUES ('Closed');

INSERT INTO lookup_order_type (description) VALUES ('New');
INSERT INTO lookup_order_type (description) VALUES ('Change');
INSERT INTO lookup_order_type (description) VALUES ('Upgrade');
INSERT INTO lookup_order_type (description) VALUES ('Downgrade');
INSERT INTO lookup_order_type (description) VALUES ('Disconnect');
INSERT INTO lookup_order_type (description) VALUES ('Move');
INSERT INTO lookup_order_type (description) VALUES ('Return');
INSERT INTO lookup_order_type (description) VALUES ('Suspend');
INSERT INTO lookup_order_type (description) VALUES ('Unsuspend');

INSERT INTO lookup_orderaddress_types (description) VALUES ('Billing');
INSERT INTO lookup_orderaddress_types (description) VALUES ('Shipping');

INSERT INTO lookup_payment_methods (description) VALUES ('Cash');
INSERT INTO lookup_payment_methods (description) VALUES ('Credit Card');
INSERT INTO lookup_payment_methods (description) VALUES ('Personal Check');
INSERT INTO lookup_payment_methods (description) VALUES ('Money Order');
INSERT INTO lookup_payment_methods (description) VALUES ('Certified Check');

INSERT INTO lookup_creditcard_types (description) VALUES ('Visa');
INSERT INTO lookup_creditcard_types (description) VALUES ('Master Card');
INSERT INTO lookup_creditcard_types (description) VALUES ('American Express');
INSERT INTO lookup_creditcard_types (description) VALUES ('Discover');

INSERT INTO lookup_payment_status (description) VALUES ('Pending');
INSERT INTO lookup_payment_status (description) VALUES ('In Progress');
INSERT INTO lookup_payment_status (description) VALUES ('Approved');
INSERT INTO lookup_payment_status (description) VALUES ('Declined');

