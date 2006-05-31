-- Product Option Configurator Result types
SET IDENTITY_INSERT [lookup_product_conf_result] ON
INSERT INTO lookup_product_conf_result (code, description, default_item, level, enabled) VALUES (1, 'integer', 0, 0, 1);
INSERT INTO lookup_product_conf_result (code, description, default_item, level, enabled) VALUES (2, 'float', 0, 0, 1);
INSERT INTO lookup_product_conf_result (code, description, default_item, level, enabled) VALUES (3, 'boolean', 0, 0, 1);
INSERT INTO lookup_product_conf_result (code, description, default_item, level, enabled) VALUES (4, 'timestamp', 0, 0, 1);
INSERT INTO lookup_product_conf_result (code, description, default_item, level, enabled) VALUES (5, 'string', 0, 0, 1);
SET IDENTITY_INSERT [lookup_product_conf_result] OFF

