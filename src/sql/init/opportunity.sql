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

insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (1, 'Minute(s)', @TRUE@, 60);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (2, 'Hour(s)', @FALSE@, 3600);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (3, 'Day(s)', @FALSE@, 86400);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (4, 'Week(s)', @FALSE@, 604800);
insert into lookup_call_reminder (level, description, default_item, base_value) VALUES (5, 'Month(s)', @FALSE@, 18144000);

INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (1, 'Yes - Business progressing', 10, @TRUE@, @TRUE@, 0, NULL, @FALSE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (2, 'No - No business at this time', 20, @TRUE@, @FALSE@, 0, NULL, @FALSE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (3, 'Unsure - Unsure or no contact made', 30, @TRUE@, @TRUE@, 0, NULL, @FALSE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (4, 'Lost to competitor', 140, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (5, 'No further interest', 150, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (6, 'Event postponed/canceled', 160, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (7, 'Another pending action', 170, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (8, 'Another contact handling event', 180, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (9, 'Contact no longer with company', 190, @TRUE@, @FALSE@, 0, NULL, @TRUE@);
INSERT INTO lookup_call_result (result_id, description, "level", enabled, next_required, next_days, next_call_type_id, canceled_type) VALUES (10, 'Servicing', 120, @TRUE@, @FALSE@, 0, NULL, @FALSE@);

INSERT INTO lookup_call_priority (level, description, default_item, weight) VALUES (1, 'Low', @TRUE@, 10);
INSERT INTO lookup_call_priority (level, description, weight ) VALUES (2, 'Medium', 20);
INSERT INTO lookup_call_priority (level, description, weight ) VALUES (3, 'High', 30);

INSERT INTO lookup_opportunity_types (description, level) VALUES ('Annuity', 0);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Consultation', 1);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Development', 2);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Maintenance', 3);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Product Sales', 4);
INSERT INTO lookup_opportunity_types (description, level) VALUES ('Services', 5);
                                                          
