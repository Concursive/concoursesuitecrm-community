INSERT INTO lookup_stage (level, order_id,description) VALUES (1, 1, 'Prospecting');
INSERT INTO lookup_stage (level, order_id,description) VALUES (2, 2, 'Qualification');
INSERT INTO lookup_stage (level, order_id,description) VALUES (3, 3, 'Needs Analysis');
INSERT INTO lookup_stage (level, order_id,description) VALUES (4, 4, 'Value Proposition');
INSERT INTO lookup_stage (level, order_id,description) VALUES (5, 5, 'Perception Analysis');
INSERT INTO lookup_stage (level, order_id,description) VALUES (6, 6, 'Proposal/Price Quote');
INSERT INTO lookup_stage (level, order_id,description) VALUES (7, 7, 'Negotiation/Review');
INSERT INTO lookup_stage (level, order_id,description) VALUES (8, 8, 'Closed Won');
INSERT INTO lookup_stage (level, order_id,description) VALUES (9, 9, 'Closed Lost');

INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Phone Call', true, 10);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('Fax', false, 20);
INSERT INTO lookup_call_types (description, default_item, level) VALUES ('In-Person', false, 30);

