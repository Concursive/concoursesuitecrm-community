INSERT INTO lookup_asset_status (level, description) VALUES (10, 'In use');
INSERT INTO lookup_asset_status (level, description) VALUES (20, 'Not in use');
INSERT INTO lookup_asset_status (level, description) VALUES (30, 'Requires maintenance');
INSERT INTO lookup_asset_status (level, description) VALUES (40, 'Retired');

INSERT INTO lookup_sc_category (level, description) VALUES (10, 'Consulting');
INSERT INTO lookup_sc_category (level, description) VALUES (20, 'Hardware Maintenance');
INSERT INTO lookup_sc_category (level, description) VALUES (30, 'Manufacturer''s Maintenance');
INSERT INTO lookup_sc_category (level, description) VALUES (40, 'Monitoring');
INSERT INTO lookup_sc_category (level, description) VALUES (50, 'Time and Materials');
INSERT INTO lookup_sc_category (level, description) VALUES (60, 'Warranty');

--INSERT INTO lookup_sc_type (level, description) VALUES (0, '');

INSERT INTO lookup_response_model (level, description) VALUES (10, 'M-F 8AM-5PM 8 hours');
INSERT INTO lookup_response_model (level, description) VALUES (20, 'M-F 8AM-5PM 6 hours');
INSERT INTO lookup_response_model (level, description) VALUES (30, 'M-F 8AM-5PM 4 hours');
INSERT INTO lookup_response_model (level, description) VALUES (40, 'M-F 8AM-5PM same day');
INSERT INTO lookup_response_model (level, description) VALUES (50, 'M-F 8AM-5PM next business day');
INSERT INTO lookup_response_model (level, description) VALUES (60, 'M-F 8AM-8PM 4 hours');
INSERT INTO lookup_response_model (level, description) VALUES (70, 'M-F 8AM-8PM 2 hours');
INSERT INTO lookup_response_model (level, description) VALUES (80, '24x7 8 hours');
INSERT INTO lookup_response_model (level, description) VALUES (90, '24x7 4 hours');
INSERT INTO lookup_response_model (level, description) VALUES (100, '24x7 2 hours');
INSERT INTO lookup_response_model (level, description) VALUES (110, 'No response time guaranteed');

INSERT INTO lookup_phone_model (level, description) VALUES (10, '< 15 minutes');
INSERT INTO lookup_phone_model (level, description) VALUES (20, '< 5 minutes');
INSERT INTO lookup_phone_model (level, description) VALUES (30, 'M-F 7AM-4PM');
INSERT INTO lookup_phone_model (level, description) VALUES (40, 'M-F 8AM-5PM');
INSERT INTO lookup_phone_model (level, description) VALUES (50, 'M-F 8AM-8PM');
INSERT INTO lookup_phone_model (level, description) VALUES (60, '24x7');
INSERT INTO lookup_phone_model (level, description) VALUES (70, 'No phone support');

INSERT INTO lookup_onsite_model (level, description) VALUES (10, 'M-F 7AM-4PM');
INSERT INTO lookup_onsite_model (level, description) VALUES (20, 'M-F 8AM-5PM');
INSERT INTO lookup_onsite_model (level, description) VALUES (30, 'M-F 8AM-8PM');
INSERT INTO lookup_onsite_model (level, description) VALUES (40, '24x7');
INSERT INTO lookup_onsite_model (level, description) VALUES (50, 'No onsite service');

INSERT INTO lookup_email_model (level, description) VALUES (10, '2 hours');
INSERT INTO lookup_email_model (level, description) VALUES (20, '4 hours');
INSERT INTO lookup_email_model (level, description) VALUES (30, 'Same day');
INSERT INTO lookup_email_model (level, description) VALUES (40, 'Next business day');

INSERT INTO lookup_hours_reason (level, description) VALUES (10, 'Purchase');
INSERT INTO lookup_hours_reason (level, description) VALUES (20, 'Renewal');
INSERT INTO lookup_hours_reason (level, description) VALUES (30, 'Correction');


