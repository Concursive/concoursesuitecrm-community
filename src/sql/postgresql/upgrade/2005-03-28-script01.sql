-- Missing key
ALTER TABLE ONLY project_news
    ADD CONSTRAINT project_news_pkey PRIMARY KEY (news_id);

-- Existing options not functional
DELETE FROM quote_product_option_integer;
DELETE FROM quote_product_options;
DELETE FROM product_option_values;
DELETE FROM product_option_map;
DELETE FROM product_option_integer;
DELETE FROM product_option_text;
DELETE FROM product_option_boolean;
DELETE FROM product_option;

-- Configurations not in use yet
DELETE FROM product_option_configurator WHERE class_name = 'org.aspcfs.modules.products.configurator.ColorConfigurator';
DELETE FROM product_option_configurator WHERE class_name = 'org.aspcfs.modules.products.configurator.DistributionConfigurator';
