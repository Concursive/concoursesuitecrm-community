INSERT INTO lookup_stage (level, order_id,description) VALUES (1, 1, 'Prospecting');
INSERT INTO lookup_stage (level, order_id,description) VALUES (2, 2, 'Qualification');
INSERT INTO lookup_stage (level, order_id,description) VALUES (3, 3, 'Needs Analysis');
INSERT INTO lookup_stage (level, order_id,description) VALUES (4, 4, 'Value Proposition');
INSERT INTO lookup_stage (level, order_id,description) VALUES (5, 5, 'Perception Analysis');
INSERT INTO lookup_stage (level, order_id,description) VALUES (6, 6, 'Proposal/Price Quote');
INSERT INTO lookup_stage (level, order_id,description) VALUES (7, 7, 'Negotiation/Review');
INSERT INTO lookup_stage (level, order_id,description) VALUES (8, 8, 'Closed Won');
INSERT INTO lookup_stage (level, order_id,description) VALUES (9, 9, 'Closed Lost');

INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Incoming Call', @TRUE@, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Outgoing Call', @FALSE@, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Proactive Call', @FALSE@, 30);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Inhouse Meeting', @FALSE@, 40);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Outside Appointment', @FALSE@, 50);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Proactive Meeting', @FALSE@, 60);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Email Servicing', @FALSE@, 70);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Email Proactive', @FALSE@, 80);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax Servicing', @FALSE@, 90);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax Proactive', @FALSE@, 100);

INSERT INTO lookup_opportunity_types (description, level) VALUES ('Annuity', 0);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Consultation', 1);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Development', 2);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Maintenance', 3);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Product Sales', 4);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Services', 5);
                                                          
