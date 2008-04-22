-- ----------------------------------------------------------------------------
--  MySQL Table Creation
--
--  @author     Andrei I. Holub
--  @created    August 2, 2006
--  @version    $Id:$
-- ----------------------------------------------------------------------------

CREATE TABLE lookup_container_menu (
  code INT AUTO_INCREMENT PRIMARY KEY,
  cname VARCHAR(300),
  description VARCHAR(300),
  default_item BOOLEAN DEFAULT FALSE,
  level INTEGER,
  enabled BOOLEAN DEFAULT TRUE,
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified TIMESTAMP NULL
);

CREATE INDEX lkupcontmenu_code_idx
  ON lookup_container_menu (code);

CREATE INDEX lkcontmenu_level_idx
  ON lookup_container_menu (`level`);

CREATE INDEX lkcontmenu_lmid_idx
  ON lookup_container_menu (link_module_id);

--CREATE UNIQUE INDEX u_lcontmenu_cname ON lookup_container_menu (cname);

CREATE TRIGGER lookup_container_menu_entries BEFORE INSERT ON  lookup_container_menu FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_webpage_priority (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  level integer,
  constant float,
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);

CREATE INDEX lkupwp_code_idx
  ON lookup_webpage_priority
  (code);

CREATE TRIGGER lookup_webpage_priority_entries BEFORE INSERT ON lookup_webpage_priority FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE lookup_sitechange_frequency (
  code INT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(300),
  default_item boolean DEFAULT false,
  level integer,
  constant VARCHAR(300),
  enabled boolean DEFAULT true,
  entered timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified timestamp NULL
);

CREATE TRIGGER lookup_sitechange_frequency_entries BEFORE INSERT ON lookup_sitechange_frequency FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE INDEX lkup_sitefreq_code_idx
  ON lookup_sitechange_frequency
  (code);

CREATE TABLE web_layout (
  layout_id INT AUTO_INCREMENT PRIMARY KEY,
  layout_constant INT UNIQUE,
  layout_name VARCHAR(300) NOT NULL,
  jsp VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN NOT NULL DEFAULT false
);

CREATE INDEX weblayout_layout_idx
  ON web_layout (layout_id);

CREATE INDEX weblayout_layout_constant_idx
  ON web_layout
  (layout_constant);

CREATE TABLE web_style (
  style_id INT AUTO_INCREMENT PRIMARY KEY,
  style_constant INT UNIQUE,
  style_name VARCHAR(300) NOT NULL,
  css VARCHAR(300),
  thumbnail VARCHAR(300),
  custom BOOLEAN NOT NULL DEFAULT false,
  layout_id INT REFERENCES web_layout(layout_id),
  active_style BOOLEAN DEFAULT false NOT NULL
);

CREATE INDEX webstyle_style_constant_idx
  ON web_style
  (style_constant);

CREATE INDEX webstyle_style_id_idx
  ON web_style
  (style_id);

CREATE INDEX webstyle_layout_id_idx
  ON web_style
  (layout_id);

CREATE TABLE web_site (
  site_id INT AUTO_INCREMENT PRIMARY KEY,
  site_name VARCHAR(300) NOT NULL,
  internal_description TEXT,
  hit_count INT,
  notes TEXT,
  enabled BOOLEAN NOT NULL DEFAULT true,
  layout_id INT REFERENCES web_layout(layout_id),
  style_id INT REFERENCES web_style(style_id),
  logo_image_id INT REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  scripts TEXT,
  url varchar (2000)
);

CREATE INDEX website_site_id_idx
  ON web_site
  (site_id);

CREATE INDEX website_style_id_idx
  ON web_site
  (style_id);

CREATE INDEX website_layout_id_idx
  ON web_site
  (layout_id);

CREATE TRIGGER web_site_entries BEFORE INSERT ON web_site FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_site_log (
  site_log_id INT AUTO_INCREMENT PRIMARY KEY,
  site_id INT references web_site(site_id),
  user_id INT references `access`(user_id),
  username VARCHAR(80) NOT NULL,
  ip VARCHAR(80),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255)
);

CREATE TRIGGER web_site_log_entries BEFORE INSERT ON web_site_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE web_tab (
  tab_id INT AUTO_INCREMENT PRIMARY KEY,
  display_text VARCHAR(300) NOT NULL,
  internal_description TEXT,
  site_id INT references web_site(site_id),
  tab_position INT NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  keywords VARCHAR(300)
);

CREATE INDEX webtab_tab_id_idx
  ON web_tab
  (tab_id);

CREATE INDEX webtab_site_id_idx
  ON web_tab
  (site_id);

CREATE INDEX webtab_tab_position_idx
  ON web_tab
  (tab_position);

CREATE TRIGGER web_tab_entries BEFORE INSERT ON web_tab FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_page_version (
  page_version_id INT AUTO_INCREMENT PRIMARY KEY,
  version_number INT NOT NULL,
  internal_description TEXT,
  notes TEXT,
  parent_page_version_id INT REFERENCES web_page_version(page_version_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX webpgvers_page_version_id_idx
  ON web_page_version
  (page_version_id);

CREATE TRIGGER web_page_version_entries BEFORE INSERT ON web_page_version FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_page_group (
  page_group_id INT AUTO_INCREMENT PRIMARY KEY,
  group_name VARCHAR(300),
  internal_description TEXT,
  group_position INT NOT NULL,
  tab_id INT REFERENCES web_tab(tab_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX webpggrep_page_group_id_idx
  ON web_page_group
  (page_group_id);

CREATE INDEX webpggrep_tab_id_idx
  ON web_page_group
  (tab_id);

CREATE INDEX webpggrep_group_position_idx
  ON web_page_group
  (group_position);

CREATE TRIGGER web_page_group_entries BEFORE INSERT ON web_page_group FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_tab_banner (
  tab_banner_id INT AUTO_INCREMENT PRIMARY KEY,
  tab_id INT REFERENCES web_tab(tab_id),
  image_id INT REFERENCES project_files(item_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX webtabbanner_tab_banner_id_idx
  ON web_tab_banner
  (tab_banner_id);

CREATE TRIGGER web_tab_banner_entries BEFORE INSERT ON web_tab_banner FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_page (
  page_id INT AUTO_INCREMENT PRIMARY KEY,
  page_name VARCHAR(300),
  page_position INT NOT NULL,
  active_page_version_id INT REFERENCES web_page_version(page_version_id),
  construction_page_version_id INT REFERENCES web_page_version(page_version_id),
  page_group_id INT REFERENCES web_page_group(page_group_id),
  tab_banner_id INT REFERENCES web_tab_banner(tab_banner_id),
  notes TEXT,
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id),
  page_alias INT REFERENCES web_page(page_id),
  link_module_id int REFERENCES permission_category(category_id),
  link_container_id int REFERENCES lookup_container_menu(code),
  keywords VARCHAR(300),
  change_freq INT REFERENCES lookup_sitechange_frequency(code),
  page_priority INT REFERENCES lookup_webpage_priority(code)
);

CREATE INDEX webpage_page_priority_idx
  ON web_page
  (page_priority);

CREATE INDEX webpage_change_freq_idx
  ON web_page
  (change_freq);

CREATE INDEX webpage_page_id_idx
  ON web_page
  (page_id);

CREATE INDEX webpage_page_group_id_idx
  ON web_page
  (page_group_id);

CREATE TRIGGER web_page_entries BEFORE INSERT ON web_page FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

ALTER TABLE web_page_version ADD COLUMN page_id INT REFERENCES web_page(page_id);

CREATE TABLE web_page_row (
  page_row_id INT AUTO_INCREMENT PRIMARY KEY,
  row_position INT NOT NULL,
  page_version_id INT REFERENCES web_page_version(page_version_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX wpr_page_row_id_idx
  ON web_page_row
  (page_row_id);

CREATE INDEX wpr_row_position_idx
  ON web_page_row
  (row_position);

CREATE INDEX wpr_page_version_id_idx
  ON web_page_row
  (page_version_id);

CREATE TRIGGER web_page_row_entries BEFORE INSERT ON web_page_row FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE web_icelet (
  icelet_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_name VARCHAR(300) NOT NULL,
  icelet_description VARCHAR(1500),
  icelet_configurator_class VARCHAR(300) NOT NULL UNIQUE,
  icelet_version INT,
  enabled BOOLEAN NOT NULL DEFAULT true
);

CREATE INDEX wi_icelet_id_idx
  ON web_icelet
  (icelet_id);

CREATE TABLE web_row_column (
  row_column_id INT AUTO_INCREMENT PRIMARY KEY,
  column_position INT NOT NULL,
  width INT,
  page_row_id INT REFERENCES web_page_row(page_row_id),
  icelet_id INT REFERENCES web_icelet(icelet_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX wrc_rc_id_idx
  ON web_row_column
  (row_column_id);

CREATE INDEX wrc_page_row_id_idx
  ON web_row_column
  (page_row_id);

CREATE INDEX wrc_icelet_id_idx
  ON web_row_column
  (icelet_id);

CREATE INDEX wrc_column_position_idx
  ON web_row_column
  (column_position);

CREATE TRIGGER web_row_column_entries BEFORE INSERT ON web_row_column FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

ALTER TABLE web_page_row ADD COLUMN row_column_id INT REFERENCES web_row_column(row_column_id);

CREATE TABLE web_icelet_property (
  property_id INT AUTO_INCREMENT  PRIMARY KEY,
  property_type_constant INT,
  property_value TEXT,
  row_column_id INT NOT NULL REFERENCES web_row_column(row_column_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE TRIGGER web_icelet_property_entries BEFORE INSERT ON web_icelet_property FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE portfolio_category (
  category_id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  category_name VARCHAR(300) NOT NULL,
  category_description TEXT,
  category_position_id INT REFERENCES portfolio_category(category_id),
  parent_category_id INT REFERENCES portfolio_category(category_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX pc_category_id_idx
  ON portfolio_category
  (category_id);

CREATE INDEX pc_parent_category_id_idx
  ON portfolio_category
  (parent_category_id);

CREATE TRIGGER portfolio_category_entries BEFORE INSERT ON portfolio_category FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

CREATE TABLE portfolio_item (
  item_id INT AUTO_INCREMENT PRIMARY KEY,
  item_name VARCHAR(300) NOT NULL,
  item_description TEXT,
  item_position_id INT REFERENCES portfolio_item(item_id),
  image_id INT REFERENCES project_files(item_id),
  caption VARCHAR(300),
  portfolio_category_id INT REFERENCES portfolio_category(category_id),
  enabled BOOLEAN NOT NULL DEFAULT true,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX pi_portfolio_category_id_idx
  ON portfolio_item
  (portfolio_category_id);

CREATE INDEX pi_item_id_idx
  ON portfolio_item
  (item_id);

CREATE INDEX pi_item_position_id_idx
  ON portfolio_item
  (item_position_id);

CREATE TRIGGER portfolio_item_entries BEFORE INSERT ON portfolio_item FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

-- Optimized to reduce database overhead
CREATE TABLE web_site_access_log (
  site_log_id INT AUTO_INCREMENT PRIMARY KEY,
  site_id INT REFERENCES web_site(site_id),
  user_id INT REFERENCES `access`(user_id),
  ip VARCHAR(300),
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  browser VARCHAR(255),
  referrer VARCHAR(1024)
);

CREATE INDEX wsal_site_log_id_idx
  ON web_site_access_log
  (site_log_id);

CREATE INDEX wsal_site_id_idx
  ON web_site_access_log
  (site_id);

CREATE TRIGGER web_site_access_log_entries BEFORE INSERT ON web_site_access_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE web_page_access_log (
  page_id INT,
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER web_page_access_log_entries BEFORE INSERT ON web_page_access_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE web_product_access_log (
  product_id INT,
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER web_product_access_log_entries BEFORE INSERT ON web_product_access_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE web_product_email_log (
  product_id INT,
  emails_to TEXT NOT NULL,
  from_name VARCHAR(300) NOT NULL,
  comments VARCHAR(1024),
  site_log_id INT,
  entered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER web_product_email_log_entries BEFORE INSERT ON web_product_email_log FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered);

CREATE TABLE url_map (
  url_id INT AUTO_INCREMENT PRIMARY KEY,
  time_in_millis NUMERIC(19) NOT NULL,
  url TEXT
);

CREATE TABLE web_page_role_map (
  page_role_map_id INT AUTO_INCREMENT PRIMARY KEY,
  web_page_id INT NOT NULL REFERENCES web_page(page_id),
  role_id INT NOT NULL REFERENCES role(role_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL REFERENCES `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL REFERENCES `access`(user_id)
);

CREATE INDEX wprm_page_role_map_id_idx
  ON web_page_role_map
  (page_role_map_id);

CREATE INDEX wprm_role_id_idx
  ON web_page_role_map
  (role_id);

CREATE INDEX wprm_web_page_id_idx
  ON web_page_role_map
  (web_page_id);

CREATE TRIGGER web_page_role_map_entries BEFORE INSERT ON web_page_role_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

create table web_icelet_dashboard_map (
  dashboard_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_module_id INT,
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE INDEX widm_dashboard_map_id_idx
  ON web_icelet_dashboard_map
  (dashboard_map_id);

CREATE INDEX widm_icelet_id_idx
  ON web_icelet_dashboard_map
  (icelet_id);

CREATE INDEX widm_link_module_id_idx
  ON web_icelet_dashboard_map
  (link_module_id);

CREATE TRIGGER web_icelet_dashboard_map_entries BEFORE INSERT ON web_icelet_dashboard_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

create table web_icelet_customtab_map (
  custom_map_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT REFERENCES web_icelet(icelet_id),
  link_container_id int REFERENCES lookup_container_menu (code),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE INDEX wictm_custom_map_id_idx
  ON web_icelet_customtab_map
  (custom_map_id);

CREATE INDEX wictm_icelet_id_idx
  ON web_icelet_customtab_map
  (icelet_id);

CREATE INDEX wictm_link_container_id_idx
  ON web_icelet_customtab_map
  (link_container_id);

CREATE TRIGGER web_icelet_customtab_map_entries BEFORE INSERT ON web_icelet_customtab_map FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);

create table web_icelet_publicwebsite (
  icelet_publicwebsite_id INT AUTO_INCREMENT PRIMARY KEY,
  icelet_id INT references web_icelet(icelet_id),
  entered TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  enteredby INT NOT NULL references `access`(user_id),
  modified TIMESTAMP NULL,
  modifiedby INT NOT NULL references `access`(user_id)
);

CREATE INDEX wip_icelet_publicwebsite_id_idx
  ON web_icelet_publicwebsite
  (icelet_publicwebsite_id);

CREATE INDEX wip_icelet_id_idx
  ON web_icelet_publicwebsite
  (icelet_id);

CREATE TRIGGER web_icelet_publicwebsite_entries BEFORE INSERT ON web_icelet_publicwebsite FOR EACH ROW SET
NEW.entered = IF(NEW.entered IS NULL OR NEW.entered = '0000-00-00 00:00:00', NOW(), NEW.entered),
NEW.modified = IF (NEW.modified IS NULL OR NEW.modified = '0000-00-00 00:00:00', NEW.entered, NEW.modified);