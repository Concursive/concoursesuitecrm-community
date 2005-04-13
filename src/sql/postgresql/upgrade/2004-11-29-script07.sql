-- Product defaults

-- Product Option Configurators
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('Color', 'Color Configurator', 'Color Configurator', 'org.aspcfs.modules.products.configurator.ColorConfigurator', 1);
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('Distribution', 'Distribution Configurator', 'Distribution Configurator', 'org.aspcfs.modules.products.configurator.DistributionConfigurator', 1);
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('String', 'String Configurator', 'String Configurator', 'org.aspcfs.modules.products.configurator.StringConfigurator', 1);
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('Checkbox', 'Checkbox Configurator', 'Checkbox Configurator', 'org.aspcfs.modules.products.configurator.CheckboxConfigurator', 1);
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('LookupList', 'LookupList Configurator', 'LookupList Configurator', 'org.aspcfs.modules.products.configurator.LookupListConfigurator', 1);
INSERT INTO product_option_configurator (configurator_name, short_description, long_description, class_name, result_type) VALUES ('Number', 'Numerical Configurator', 'Numerical Configurator', 'org.aspcfs.modules.products.configurator.NumericalConfigurator', 1);

-- Lookup Recurring Type
INSERT INTO lookup_recurring_type (code, description, default_item, level, enabled) VALUES (1, 'Weekly', false, 0, true);
INSERT INTO lookup_recurring_type (code, description, default_item, level, enabled) VALUES (2, 'Monthly', false, 0, true);
INSERT INTO lookup_recurring_type (code, description, default_item, level, enabled) VALUES (3, 'Yearly', false, 0, true);
